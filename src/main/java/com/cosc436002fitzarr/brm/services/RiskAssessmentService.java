package com.cosc436002fitzarr.brm.services;


import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.input.AttachRiskAssessmentsToBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.input.RiskAssessmentAttachmentInput;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessmentSchedule;
import com.cosc436002fitzarr.brm.models.riskassessment.input.CreateRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.riskassessment.input.DeleteRiskAssessmentsInput;
import com.cosc436002fitzarr.brm.models.riskassessment.input.GetAllRiskAssessmentsBySiteInput;
import com.cosc436002fitzarr.brm.models.riskassessment.input.UpdateRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.WorkplaceHealthSafetyMember;
import com.cosc436002fitzarr.brm.repositories.RiskAssessmentRepository;
import com.cosc436002fitzarr.brm.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RiskAssessmentService {
    @Autowired
    public RiskAssessmentRepository riskAssessmentRepository;
    @Autowired
    public WorkplaceHealthSafetyMemberService workplaceHealthSafetyMemberService;
    @Autowired
    public SiteMaintenanceAssociateService siteMaintenanceAssociateService;

    private static Logger LOGGER = LoggerFactory.getLogger(RiskAssessmentService.class);

    public RiskAssessment createRiskAssessment(CreateRiskAssessmentInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<EntityTrail> entityTrail = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedRiskAssessmentMessage()));
        List<RiskAssessmentSchedule> riskAssessmentSchedules = new ArrayList<>();

        RiskAssessment riskAssessmentForPersistence = new RiskAssessment(
            id,
            currentTime,
            currentTime,
            entityTrail,
            input.getPublisherId(),
            input.getTitle(),
            input.getTaskDescription(),
            input.getHazards(),
            input.getScreeners(),
            riskAssessmentSchedules
        );

        try {
            riskAssessmentRepository.save(riskAssessmentForPersistence);
            LOGGER.info("Persisted new risk assessment: " + riskAssessmentForPersistence.toString() + " to the risk assessment repository");
        } catch(Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }
        workplaceHealthSafetyMemberService.attachRiskAssessmentIdToWorkplaceHealthSafetyMemberIdList(id, input.getPublisherId());
        return riskAssessmentForPersistence;
    }

    public RiskAssessment updateRiskAssessment(UpdateRiskAssessmentInput input) {
        RiskAssessment updatedRiskAssessment = getUpdatedRiskAssessment(input.getId(), input.getPublisherId());

        RiskAssessment updatedRiskAssessmentForPersistence = new RiskAssessment(
            updatedRiskAssessment.getId(),
            updatedRiskAssessment.getCreatedAt(),
            updatedRiskAssessment.getUpdatedAt(),
            updatedRiskAssessment.getEntityTrail(),
            updatedRiskAssessment.getPublisherId(),
            input.getTitle(),
            input.getTaskDescription(),
            input.getHazards(),
            input.getScreeners(),
            input.getRiskAssessmentSchedules()
        );

        try {
            riskAssessmentRepository.save(updatedRiskAssessmentForPersistence);
            LOGGER.info("Risk assessment " + updatedRiskAssessmentForPersistence.toString() + " saved in risk assessment repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedRiskAssessmentForPersistence;
    }

    public RiskAssessment getUpdatedRiskAssessment(String existingRiskAssessmentId, String publisherId) {
        Optional<RiskAssessment> potentialRiskAssessment = riskAssessmentRepository.findById(existingRiskAssessmentId);
        if (!potentialRiskAssessment.isPresent()) {
            LOGGER.info("Risk assessment with id: " + existingRiskAssessmentId + " not found in risk assessment repository");
            throw new EntityNotFoundException();
        }

        RiskAssessment existingRiskAssessment = potentialRiskAssessment.get();

        LOGGER.info("Successfully retrieved risk assessment: " + existingRiskAssessment.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, publisherId, getUpdatedRiskAssessmentMessage());

        List<EntityTrail> existingTrail = existingRiskAssessment.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        existingRiskAssessment.setEntityTrail(updatedTrail);

        existingRiskAssessment.setUpdatedAt(currentTime);

        return existingRiskAssessment;
    }

    public RiskAssessment getById(String id) {
        return checkRiskAssessmentExists(id);
    }

    public Map<String, Object> getRiskAssessmentsBySite(GetAllRiskAssessmentsBySiteInput input) {
        Sort sortProperty = Sort.by(input.getPageInput().getSortDirection(), input.getPageInput().getSortBy());
        Pageable pageInput = PageRequest.of(input.getPageInput().getPageNo().intValue(), input.getPageInput().getPageSize().intValue(), sortProperty);

        Page<RiskAssessment> sortedRiskAssessmentsInPage = riskAssessmentRepository.findAll(pageInput);
        List<RiskAssessment> riskAssessmentContent = sortedRiskAssessmentsInPage.getContent();

        List<WorkplaceHealthSafetyMember> filteredWorkplaceHealthSafetyMembers = workplaceHealthSafetyMemberService.getWorkplaceHealthSafetyMembersBySite(input.getAssociatedSiteIds());
        List<String> whsMemberSubmittedRiskAssessmentIds = new ArrayList<>();
        for (WorkplaceHealthSafetyMember whsMember : filteredWorkplaceHealthSafetyMembers) {
            whsMemberSubmittedRiskAssessmentIds.addAll(whsMember.getRiskAssessmentsFiledIds());
        }

        List<RiskAssessment> filteredRiskAssessmentContent = riskAssessmentContent
                .stream()
                .filter(riskAssessment -> whsMemberSubmittedRiskAssessmentIds.contains(riskAssessment.getId()))
                .collect(Collectors.toList());

        Map<String, Object> riskAssessmentMapResponse = PageUtils.getRiskAssessmentMappingResponse(sortedRiskAssessmentsInPage, filteredRiskAssessmentContent);
        return riskAssessmentMapResponse;
    }

    public void attachRiskAssessmentSchedulesToRiskAssessment(AttachRiskAssessmentsToBuildingRiskAssessmentInput input) {
        for (RiskAssessmentAttachmentInput riskAssessmentAttachmentInput : input.getRiskAssessmentAttachmentInputList()) {
            Set<String> siteMaintenanceAssociatesUpdated = new HashSet<>();

            RiskAssessment riskAssessmentToAttachNewBuildingRiskAssessment = checkRiskAssessmentExists(riskAssessmentAttachmentInput.getRiskAssessmentId());

            RiskAssessment updatedAssessmentForPersistence = getUpdatedRiskAssessment(riskAssessmentToAttachNewBuildingRiskAssessment.getId(), input.getPublisherId());

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(riskAssessmentAttachmentInput.getDueDate());

            LocalDateTime dueDate = LocalDateTime.parse(riskAssessmentAttachmentInput.getDueDate(), dateTimeFormatter);

            RiskAssessmentSchedule newAssessmentSchedule = new RiskAssessmentSchedule(
                riskAssessmentAttachmentInput.getBuildingId(),
                Status.IN_PROGRESS,
                dueDate,
                RiskLevel.EMPTY,
                riskAssessmentAttachmentInput.getSiteMaintenanceAssociateIds(),
                riskAssessmentAttachmentInput.getWorkOrder()
            );

            List<RiskAssessmentSchedule> updatedAssessmentScheduleList = updatedAssessmentForPersistence.getRiskAssessmentSchedules();

            updatedAssessmentScheduleList.add(newAssessmentSchedule);

            updatedAssessmentForPersistence.setRiskAssessmentSchedules(updatedAssessmentScheduleList);

            try {
                riskAssessmentRepository.save(updatedAssessmentForPersistence);
                LOGGER.info("Risk assessment with id: " + updatedAssessmentForPersistence.toString() + " updated in " +
                        "risk assessment repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
            for (String siteMaintenanceAssociateId : riskAssessmentAttachmentInput.getSiteMaintenanceAssociateIds()) {
                // Prevent Duplicate risk assessment Id's from being stored in the same site maintenance associate 'assignedRiskAssessmentIds' list attribute.
                // Ex: Jamie Collins has 2 risk assessment schedules which he's assigned to on this new risk assessment. Because we're looping through each
                // schedule here, each schedule will only belong to one risk assessment. So we only need to update the site maintenance associate id List once.
                if (!siteMaintenanceAssociatesUpdated.contains(siteMaintenanceAssociateId)) {
                    siteMaintenanceAssociateService.assignRiskAssessmentToSiteMaintenanceAssociate(siteMaintenanceAssociateId, riskAssessmentAttachmentInput.getRiskAssessmentId(), input.getPublisherId());
                    siteMaintenanceAssociatesUpdated.add(siteMaintenanceAssociateId);
                }
            }
        }
    }

    public RiskAssessment deleteRiskAssessment(String id, String publisherId) {
        RiskAssessment riskAssessmentToDelete = checkRiskAssessmentExists(id);
        riskAssessmentRepository.deleteById(id);
        LOGGER.info("Risk assessment: " + riskAssessmentToDelete.toString() + " successfully fetched and deleted from risk assessment repository");
        removeDeletedRiskAssessmentFromSiteMaintenanceAssociateSchedule(riskAssessmentToDelete.getRiskAssessmentSchedules(), riskAssessmentToDelete.getId(), publisherId);
        workplaceHealthSafetyMemberService.removeRiskAssessmentIdFromWorkplaceHealthSafetyMemberIdList(riskAssessmentToDelete.getId(), publisherId);
        return riskAssessmentToDelete;
    }

    // TODO: This API is called when
    // 1 - A building risk assessment is deleted
    // 2 - A site maintenance manager is deleted
    public void deleteRiskAssessments(DeleteRiskAssessmentsInput input) {
        for (String riskAssessmentId : input.getRiskAssessmentIds()) {
            deleteRiskAssessment(riskAssessmentId, input.getPublisherId());
        }
    }

    // This API will be called when
    // 1 - A WHS Member is deleted (In this use case, we can't reuse deleteRiskAssessment because there is no need
    // to update the WHS Member's ID List as they've already been deleted. This is seen in the deleteRiskAssessment function where the
    // workplaceHealthSafety riskAssessmentIdList is updated after the risk assessment is deleted.)
    public void deleteRiskAssessmentsAfterWHSMemberDeleted(List<String> riskAssessmentIds, String publisherId) {
        for (String riskAssessmentId : riskAssessmentIds) {
            RiskAssessment riskAssessmentToDelete = checkRiskAssessmentExists(riskAssessmentId);

            try {
                riskAssessmentRepository.deleteById(riskAssessmentToDelete.getId());
                LOGGER.info("Risk assessment: " + riskAssessmentToDelete.toString() + " successfully fetched and deleted from risk assessment repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
            removeDeletedRiskAssessmentFromSiteMaintenanceAssociateSchedule(riskAssessmentToDelete.getRiskAssessmentSchedules(), riskAssessmentToDelete.getId(), publisherId);
        }
    }

    public void removeDeletedRiskAssessmentFromSiteMaintenanceAssociateSchedule(List<RiskAssessmentSchedule> schedules, String riskAssessmentToDeleteId, String publisherId) {
        for (RiskAssessmentSchedule schedule : schedules) {
            List<String> siteMaintenanceAssociatesAttachedToDeletedSchedule = schedule.getSiteMaintenanceAssociateIds();
            for (String siteMaintenanceAssociateId : siteMaintenanceAssociatesAttachedToDeletedSchedule) {
                siteMaintenanceAssociateService.removeAssignedRiskAssessmentFromAssociate(siteMaintenanceAssociateId, riskAssessmentToDeleteId, publisherId);
            }
        }
    }

    public RiskAssessment checkRiskAssessmentExists(String id) {
        Optional<RiskAssessment> potentialRiskAssessment = riskAssessmentRepository.findById(id);
        Boolean riskAssessmentPresent = potentialRiskAssessment.isPresent();
        if (!riskAssessmentPresent) {
            LOGGER.info("Risk assessment with id: " + id + " not found in the risk assessment repository!");
            throw new EntityNotFoundException("Risk assessment with id: " + id + " not found in the risk assessment repository!");
        }
        LOGGER.info("Risk assessment: " + potentialRiskAssessment.get().toString() + " successfully fetched and deleted from risk assessment repository");
        RiskAssessment existingRiskAssessment = potentialRiskAssessment.get();
        return existingRiskAssessment;
    }

    public void removeDeletedSiteMaintenanceAssociateFromRiskAssessmentSchedule(List<String> riskAssessmentIds, String deletedSiteMaintenanceAssociateId, String publisherId) {
        for (String riskAssessmentId : riskAssessmentIds) {
            RiskAssessment existingRiskAssessment = checkRiskAssessmentExists(riskAssessmentId);

            RiskAssessment updatedRiskAssessment = getUpdatedRiskAssessment(existingRiskAssessment.getId(), publisherId);

            List<RiskAssessmentSchedule> updatedRiskAssessmentRiskAssessmentSchedules = updatedRiskAssessment.getRiskAssessmentSchedules();

            // TODO: Can use a query here instead. Review other delete user methods and update accordingly
            for (RiskAssessmentSchedule riskAssessmentSchedule : updatedRiskAssessmentRiskAssessmentSchedules) {
                List<String> siteMaintenanceAssociatesAttachedToSchedule = riskAssessmentSchedule.getSiteMaintenanceAssociateIds();
                siteMaintenanceAssociatesAttachedToSchedule.removeAll(Collections.singleton(deletedSiteMaintenanceAssociateId));
                riskAssessmentSchedule.setSiteMaintenanceAssociateIds(siteMaintenanceAssociatesAttachedToSchedule);
            }

            updatedRiskAssessment.setRiskAssessmentSchedules(updatedRiskAssessmentRiskAssessmentSchedules);
            try {
                riskAssessmentRepository.save(updatedRiskAssessment);
                LOGGER.info("Risk assessment: " + updatedRiskAssessment.toString() + " updated in risk assessment repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    public String getCreatedRiskAssessmentMessage() {
        return "CREATED RISK ASSESSMENT";
    }

    public String getUpdatedRiskAssessmentMessage() {
        return "UPDATED RISK ASSESSMENT";
    }
}
