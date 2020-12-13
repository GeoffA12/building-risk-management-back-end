package com.cosc436002fitzarr.brm.services;


import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.input.AttachRiskAssessmentsToBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.input.RiskAssessmentAttachmentInput;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessmentSchedule;
import com.cosc436002fitzarr.brm.models.riskassessment.input.CreateRiskAssessmentInput;
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
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        RiskAssessment updatedRiskAssessment = getUpdatedRiskAssessment(input.getId(), input.getPublisherId());

        RiskAssessment updatedRiskAssessmentForPersistence = new RiskAssessment(
            updatedRiskAssessment.getId(),
            updatedRiskAssessment.getCreatedAt(),
            currentTime,
            updatedRiskAssessment.getEntityTrail(),
            input.getPublisherId(),
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
        Optional<RiskAssessment> potentialRiskAssessment = riskAssessmentRepository.findById(id);
        Boolean riskAssessmentIsPresent = potentialRiskAssessment.isPresent();
        if (!riskAssessmentIsPresent) {
            LOGGER.info("Risk assessment with id: " + id + " not found in the risk assessment repository!");
            throw new EntityNotFoundException("Risk assessment with id: " + id + " not found in the risk assessment repository!");
        }
        else {
            LOGGER.info("Risk assessment: " + potentialRiskAssessment.get().toString() + " successfully fetched from risk assessment repository");
            return potentialRiskAssessment.get();
        }
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

    public RiskAssessment deleteRiskAssessment(String id, String publisherId) {
        RiskAssessment assessmentToDelete = checkRiskAssessmentExists(id);
        riskAssessmentRepository.deleteById(id);
        LOGGER.info("Risk assessment: " + assessmentToDelete.toString() + " successfully fetched and deleted from risk assessment repository");
        workplaceHealthSafetyMemberService.removeRiskAssessmentIdFromWorkplaceHealthSafetyMemberIdList(assessmentToDelete.getId(), publisherId);
        return assessmentToDelete;
    }

    public void attachRiskAssessmentSchedulesToRiskAssessment(AttachRiskAssessmentsToBuildingRiskAssessmentInput input) {
        for (RiskAssessmentAttachmentInput riskAssessmentAttachmentInput : input.getRiskAssessmentAttachmentInputList()) {

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
                siteMaintenanceAssociateService.assignRiskAssessmentToSiteMaintenanceAssociate(siteMaintenanceAssociateId, riskAssessmentAttachmentInput.getRiskAssessmentId(), input.getPublisherId());
            }
        }
    }

    // TODO: This API Should be publicly accessible and should be invoked whenever after a building risk assessment is deleted.
    public void removeRiskAssessmentSchedulesFromRiskAssessment(AttachRiskAssessmentsToBuildingRiskAssessmentInput input) {
        for (RiskAssessmentAttachmentInput riskAssessmentAttachmentInput : input.getRiskAssessmentAttachmentInputList()) {
            RiskAssessment existingRiskAssessment = checkRiskAssessmentExists(riskAssessmentAttachmentInput.getRiskAssessmentId());

            RiskAssessment updatedRiskAssessment = getUpdatedRiskAssessment(existingRiskAssessment.getId(), input.getPublisherId());

            List<RiskAssessmentSchedule> existingSchedules = updatedRiskAssessment.getRiskAssessmentSchedules();

            existingSchedules.remove(riskAssessmentAttachmentInput);

            updatedRiskAssessment.setRiskAssessmentSchedules(existingSchedules);

            try {
                riskAssessmentRepository.save(updatedRiskAssessment);
                LOGGER.info("Risk assessment with id: " + updatedRiskAssessment.toString() + " updated in " +
                        "risk assessment repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }

            for (String siteMaintenanceAssociateId : riskAssessmentAttachmentInput.getSiteMaintenanceAssociateIds()) {
                siteMaintenanceAssociateService.removeAssignedRiskAssessmentFromAssociate(siteMaintenanceAssociateId, riskAssessmentAttachmentInput.getRiskAssessmentId(), input.getPublisherId());
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

    public String getCreatedRiskAssessmentMessage() {
        return "CREATED RISK ASSESSMENT";
    }

    public String getUpdatedRiskAssessmentMessage() {
        return "UPDATED RISK ASSESSMENT";
    }
}
