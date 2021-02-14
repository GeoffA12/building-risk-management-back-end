package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.RiskAssessmentSchedule;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.input.*;
import com.cosc436002fitzarr.brm.models.sitemaintenanceassociate.SiteMaintenanceAssociate;
import com.cosc436002fitzarr.brm.repositories.RiskAssessmentScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RiskAssessmentScheduleService {
    @Autowired
    public RiskAssessmentScheduleRepository riskAssessmentScheduleRepository;
    @Autowired
    public SiteMaintenanceAssociateService siteMaintenanceAssociateService;
    @Autowired
    public RiskAssessmentService riskAssessmentService;

    private static Logger LOGGER = LoggerFactory.getLogger(RiskAssessmentScheduleService.class);

    public RiskAssessmentSchedule createRiskAssessmentSchedule(CreateRiskAssessmentScheduleInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<EntityTrail> entityTrail = new ArrayList<>(Arrays.asList(new EntityTrail(currentTime, input.getPublisherId(), getCreatedRiskAssessmentScheduleSystemComment())));

        LocalDateTime dueDate = convertDateTimeStringToLocalDateTime(input.getDueDate());

        RiskAssessmentSchedule riskAssessmentScheduleForPersistence = new RiskAssessmentSchedule(
            id,
            currentTime,
            currentTime,
            entityTrail,
            input.getPublisherId(),
            input.getTitle(),
            input.getRiskAssessmentId(),
            input.getBuildingRiskAssessmentId(),
            input.getSiteMaintenanceAssociateIds(),
            Status.IN_PROGRESS,
            input.getWorkOrder(),
            input.getHazards(),
            input.getScreeners(),
            input.getRiskLevel(),
            dueDate
        );

        try {
            riskAssessmentScheduleRepository.save(riskAssessmentScheduleForPersistence);
            LOGGER.info("Risk assessment schedule: " + riskAssessmentScheduleForPersistence.toString() + " saved in risk assessment schedule repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }

        // Add new site maintenance associates to this persisted schedule. Add the schedule id to the risk assessment id list attribute.
        riskAssessmentService.addRiskAssessmentScheduleToRiskAssessment(riskAssessmentScheduleForPersistence.getId(), riskAssessmentScheduleForPersistence.getRiskAssessmentId(), input.getPublisherId());
        for (String siteMaintenanceAssociateId : riskAssessmentScheduleForPersistence.getSiteMaintenanceAssociateIds()) {
            siteMaintenanceAssociateService.assignRiskAssessmentScheduleToSiteMaintenanceAssociate(siteMaintenanceAssociateId, riskAssessmentScheduleForPersistence.getId(), input.getPublisherId());
        }
        return riskAssessmentScheduleForPersistence;
    }

    public RiskAssessmentSchedule checkRiskAssessmentScheduleExists(String id) {
        Optional<RiskAssessmentSchedule> potentialRiskAssessmentSchedule = riskAssessmentScheduleRepository.findById(id);

        if (!potentialRiskAssessmentSchedule.isPresent()) {
            LOGGER.info("Risk assessment schedule with id: " + id + " not found in risk assessment schedule repository");
            throw new EntityNotFoundException();
        }

        RiskAssessmentSchedule riskAssessmentSchedule = potentialRiskAssessmentSchedule.get();
        LOGGER.info("Risk assessment schedule: " + riskAssessmentSchedule.toString() + " successfully fetched from risk assessment schedule repository");
        return riskAssessmentSchedule;
    }

    public RiskAssessmentSchedule getUpdatedRiskAssessmentSchedule(String id, String publisherId) {
        RiskAssessmentSchedule riskAssessmentSchedule = checkRiskAssessmentScheduleExists(id);

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateEntityTrail = new EntityTrail(currentTime, publisherId, getUpdatedRiskAssessmentScheduleSystemComment());

        List<EntityTrail> updatedEntityTrail = new ArrayList<>(riskAssessmentSchedule.getEntityTrail());

        updatedEntityTrail.add(updateEntityTrail);

        riskAssessmentSchedule.setUpdatedAt(currentTime);

        riskAssessmentSchedule.setEntityTrail(updatedEntityTrail);

        return riskAssessmentSchedule;
    }

    public RiskAssessmentSchedule getById(String id) {
        return checkRiskAssessmentScheduleExists(id);
    }

    public RiskAssessmentSchedule updateRiskAssessmentSchedule(UpdateRiskAssessmentScheduleInput input) {
        RiskAssessmentSchedule existingRiskAssessmentSchedule = checkRiskAssessmentScheduleExists(input.getId());

        RiskAssessmentSchedule updatedRiskAssessmentSchedule = getUpdatedRiskAssessmentSchedule(existingRiskAssessmentSchedule.getId(), input.getCreateRiskAssessmentScheduleInput().getPublisherId());

        LocalDateTime updatedDueDate = convertDateTimeStringToLocalDateTime(input.getCreateRiskAssessmentScheduleInput().getDueDate());

        Status updatedScheduleStatus = updatedRiskAssessmentSchedule.getStatus();
        if (input.getCreateRiskAssessmentScheduleInput().getSiteMaintenanceAssociateIds().size() == 0) {
            updatedScheduleStatus = Status.NOT_ASSIGNED;
        }

        RiskAssessmentSchedule updatedRiskAssessmentScheduleForPersistence = new RiskAssessmentSchedule(
            updatedRiskAssessmentSchedule.getId(),
            updatedRiskAssessmentSchedule.getCreatedAt(),
            updatedRiskAssessmentSchedule.getUpdatedAt(),
            updatedRiskAssessmentSchedule.getEntityTrail(),
            updatedRiskAssessmentSchedule.getPublisherId(),
            input.getCreateRiskAssessmentScheduleInput().getTitle(),
            input.getCreateRiskAssessmentScheduleInput().getRiskAssessmentId(),
            input.getCreateRiskAssessmentScheduleInput().getBuildingRiskAssessmentId(),
            input.getCreateRiskAssessmentScheduleInput().getSiteMaintenanceAssociateIds(),
            updatedScheduleStatus,
            input.getCreateRiskAssessmentScheduleInput().getWorkOrder(),
            input.getCreateRiskAssessmentScheduleInput().getHazards(),
            input.getCreateRiskAssessmentScheduleInput().getScreeners(),
            input.getCreateRiskAssessmentScheduleInput().getRiskLevel(),
            updatedDueDate
        );

        try {
            riskAssessmentScheduleRepository.save(updatedRiskAssessmentScheduleForPersistence);
            LOGGER.info("Risk assessment schedule: " + updatedRiskAssessmentScheduleForPersistence.toString() + " successfully updated and saved in risk assessment schedule repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }

        // Site maintenance associates can either be added or removed on the update, so we need to make sure we're still keeping track of which associates
        // are assigned to this risk assessment schedule.

        // Check to see which site maintenance associates were possibly removed from the schedule on the update.
        for (String siteMaintenanceAssociateId : existingRiskAssessmentSchedule.getSiteMaintenanceAssociateIds()) {
            Boolean siteMaintenanceAssociateRemovedFromSchedule = !updatedRiskAssessmentScheduleForPersistence.getSiteMaintenanceAssociateIds().contains(siteMaintenanceAssociateId);
            if (siteMaintenanceAssociateRemovedFromSchedule) {
                siteMaintenanceAssociateService.removeAssignedRiskAssessmentFromAssociate(siteMaintenanceAssociateId, updatedRiskAssessmentSchedule.getId(), input.getCreateRiskAssessmentScheduleInput().getPublisherId());
            }
        }

        // Check to see if any site maintenance associates were added to the schedule on the update
        for (String siteMaintenanceAssociateId : updatedRiskAssessmentScheduleForPersistence.getSiteMaintenanceAssociateIds()) {
            Boolean siteMaintenanceAssociateAddedToSchedule = !existingRiskAssessmentSchedule.getSiteMaintenanceAssociateIds().contains(siteMaintenanceAssociateId);
            SiteMaintenanceAssociate siteMaintenanceAssociate = siteMaintenanceAssociateService.getById(siteMaintenanceAssociateId);
            Boolean siteMaintenanceAssociateNotAlreadyAddedToSchedule = !siteMaintenanceAssociate.getAssignedRiskAssessmentScheduleIds().contains(updatedRiskAssessmentSchedule.getId());
            if (siteMaintenanceAssociateAddedToSchedule && siteMaintenanceAssociateNotAlreadyAddedToSchedule) {
                siteMaintenanceAssociateService.assignRiskAssessmentScheduleToSiteMaintenanceAssociate(siteMaintenanceAssociateId, updatedRiskAssessmentSchedule.getId(), input.getCreateRiskAssessmentScheduleInput().getPublisherId());
            }
        }

        return updatedRiskAssessmentScheduleForPersistence;
    }

    public RiskAssessmentSchedule deleteRiskAssessmentSchedule(String riskAssessmentScheduleId, String publisherId) {
        RiskAssessmentSchedule riskAssessmentScheduleToDelete = checkRiskAssessmentScheduleExists(riskAssessmentScheduleId);

        // If we delete a risk assessment schedule we also need to update the id list of the risk assessment it was attached to and remove the schedule
        // from any associates who were assigned to it.
        riskAssessmentService.removeDeletedRiskAssessmentScheduleFromRiskAssessment(riskAssessmentScheduleToDelete.getRiskAssessmentId(), riskAssessmentScheduleToDelete.getId(), publisherId);
        for (String siteMaintenanceAssociateId : riskAssessmentScheduleToDelete.getSiteMaintenanceAssociateIds()) {
            siteMaintenanceAssociateService.removeAssignedRiskAssessmentFromAssociate(siteMaintenanceAssociateId, riskAssessmentScheduleToDelete.getId(), publisherId);
        }

        try {
            riskAssessmentScheduleRepository.deleteById(riskAssessmentScheduleToDelete.getId());
            LOGGER.info("Risk assessment schedule: " + riskAssessmentScheduleToDelete.toString() + " deleted from risk assessment schedule repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }

        return riskAssessmentScheduleToDelete;
    }

    public void removeDeletedSiteMaintenanceAssociateFromRiskAssessmentSchedules(List<String> riskAssessmentScheduleIds, String deletedSiteMaintenanceAssociateId, String publisherId) {
        for (String riskAssessmentScheduleId : riskAssessmentScheduleIds) {
            RiskAssessmentSchedule existingRiskAssessmentSchedule = checkRiskAssessmentScheduleExists(riskAssessmentScheduleId);

            RiskAssessmentSchedule updatedRiskAssessmentSchedule = getUpdatedRiskAssessmentSchedule(existingRiskAssessmentSchedule.getId(), publisherId);

            List<String> siteMaintenanceAssociateIdsOfSchedule = new ArrayList<>(updatedRiskAssessmentSchedule.getSiteMaintenanceAssociateIds());

            siteMaintenanceAssociateIdsOfSchedule.remove(deletedSiteMaintenanceAssociateId);

            updatedRiskAssessmentSchedule.setSiteMaintenanceAssociateIds(siteMaintenanceAssociateIdsOfSchedule);

            if (updatedRiskAssessmentSchedule.getSiteMaintenanceAssociateIds().size() == 0) {
                updatedRiskAssessmentSchedule.setStatus(Status.NOT_ASSIGNED);
            }

            try {
                riskAssessmentScheduleRepository.save(updatedRiskAssessmentSchedule);
                LOGGER.info("Risk assessment schedule: " + updatedRiskAssessmentSchedule.toString() + " updated and saved in risk assessment schedule repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    public void attachBuildingRiskAssessmentIdToRiskAssessmentSchedules(AttachBuildingRiskAssessmentIdToRiskAssessmentScheduleInput input) {
        for (RiskAssessmentSchedule riskAssessmentSchedule : input.getRiskAssessmentScheduleList()) {
            RiskAssessmentSchedule existingRiskAssessmentSchedule = getUpdatedRiskAssessmentSchedule(riskAssessmentSchedule.getId(), input.getPublisherId());

            existingRiskAssessmentSchedule.setBuildingRiskAssessmentId(input.getBuildingRiskAssessmentId());

            try {
                riskAssessmentScheduleRepository.save(existingRiskAssessmentSchedule);
                LOGGER.info("Risk assessment schedule: " + existingRiskAssessmentSchedule.toString() + " saved in risk assessment schedule repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesByBuildingRiskAssessmentId(String buildingRiskAssessmentId) {
        return riskAssessmentScheduleRepository.getRiskAssessmentSchedulesByBuildingRiskAssessmentId(buildingRiskAssessmentId);
    }

    public LocalDateTime convertDateTimeStringToLocalDateTime(String dueDateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        TemporalAccessor tp = dateTimeFormatter.parse(dueDateString);

        LocalDateTime localDateTime = LocalDateTime.from(tp);

        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());

        LocalDateTime dueDate = LocalDateTime.ofInstant(Instant.from(zonedDateTime), ZoneOffset.UTC);

        return dueDate;
    }

    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesByRiskAssessmentIdListOfBuilding(GetBulkRiskAssessmentScheduleInput input) {
        return riskAssessmentScheduleRepository.getRiskAssessmentSchedulesByRiskAssessmentIdList(input.getRiskAssessmentIds());
    }

    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsList(GetRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsListInput input) {
        return riskAssessmentScheduleRepository.getRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsList(input.getRiskAssessmentScheduleIds());
    }

    public void deleteRiskAssessmentSchedulesFromDeletedBuildingRiskAssessment(String buildingRiskAssessmentId, String publisherId) {
        List<RiskAssessmentSchedule> riskAssessmentSchedulesOfBuildingRiskAssessment = riskAssessmentScheduleRepository.getRiskAssessmentSchedulesByBuildingRiskAssessmentId(buildingRiskAssessmentId);

        LOGGER.info(riskAssessmentSchedulesOfBuildingRiskAssessment.toString());
        for (RiskAssessmentSchedule riskAssessmentSchedule : riskAssessmentSchedulesOfBuildingRiskAssessment) {
            deleteRiskAssessmentSchedule(riskAssessmentSchedule.getId(), publisherId);
        }
    }

    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesOfSiteMaintenanceManager(List<String> riskAssessmentScheduleIds, Boolean activeSchedules) {
        List<RiskAssessmentSchedule> riskAssessmentSchedulesOfMaintenanceManager = riskAssessmentScheduleRepository.getRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsList(riskAssessmentScheduleIds);

        return riskAssessmentSchedulesOfMaintenanceManager.stream()
                .filter(riskAssessmentSchedule -> activeSchedules ? !riskAssessmentSchedule.getStatus().equals(Status.COMPLETE) : riskAssessmentSchedule.getStatus().equals(Status.COMPLETE))
                .collect(Collectors.toList());
    }

    public RiskAssessmentSchedule submitRiskAssessmentSchedule(SubmitRiskAssessmentScheduleInput input) {
        RiskAssessmentSchedule existingRiskAssessmentSchedule = checkRiskAssessmentScheduleExists(input.getId());

        RiskAssessmentSchedule updatedRiskAssessmentSchedule = getUpdatedRiskAssessmentSchedule(existingRiskAssessmentSchedule.getId(), input.getPublisherId());

        RiskAssessmentSchedule submittedRiskAssessmentScheduleForPersistence = new RiskAssessmentSchedule(
            updatedRiskAssessmentSchedule.getId(),
            updatedRiskAssessmentSchedule.getCreatedAt(),
            updatedRiskAssessmentSchedule.getUpdatedAt(),
            updatedRiskAssessmentSchedule.getEntityTrail(),
            updatedRiskAssessmentSchedule.getPublisherId(),
            updatedRiskAssessmentSchedule.getTitle(),
            updatedRiskAssessmentSchedule.getRiskAssessmentId(),
            updatedRiskAssessmentSchedule.getBuildingRiskAssessmentId(),
            updatedRiskAssessmentSchedule.getSiteMaintenanceAssociateIds(),
            Status.COMPLETE,
            updatedRiskAssessmentSchedule.getWorkOrder(),
            input.getHazards(),
            input.getScreeners(),
            input.getRiskLevel(),
            updatedRiskAssessmentSchedule.getDueDate()
        );

        try {
            riskAssessmentScheduleRepository.save(submittedRiskAssessmentScheduleForPersistence);
            LOGGER.info("Successfully submitted risk assessment schedule: " + submittedRiskAssessmentScheduleForPersistence.toString() +
                    " to the risk assessment schedule repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return submittedRiskAssessmentScheduleForPersistence;
    }

    public String getCreatedRiskAssessmentScheduleSystemComment() {
        return "CREATED RISK ASSESSMENT SCHEDULE";
    }

    public String getUpdatedRiskAssessmentScheduleSystemComment() {
        return "UPDATED RISK ASSESSMENT SCHEDULE";
    }
}
