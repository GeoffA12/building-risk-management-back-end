package com.cosc436002fitzarr.brm.services;


import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
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
import java.time.*;
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
    @Autowired
    public RiskAssessmentScheduleService riskAssessmentScheduleService;

    private static Logger LOGGER = LoggerFactory.getLogger(RiskAssessmentService.class);

    public RiskAssessment createRiskAssessment(CreateRiskAssessmentInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<EntityTrail> entityTrail = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedRiskAssessmentMessage()));
        List<String> riskAssessmentScheduleIds = new ArrayList<>();

        RiskAssessment riskAssessmentForPersistence = new RiskAssessment(
            id,
            currentTime,
            currentTime,
            entityTrail,
            input.getPublisherId(),
            input.getTitle(),
            input.getTaskDescription(),
            riskAssessmentScheduleIds
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
            input.getRiskAssessmentScheduleIds()
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

    // Invoked when a new risk assessment schedule is persisted.
    public void addRiskAssessmentScheduleToRiskAssessment(String riskAssessmentScheduleId, String riskAssessmentId, String publisherId) {
        RiskAssessment existingRiskAssessment = checkRiskAssessmentExists(riskAssessmentId);

        if (existingRiskAssessment.getRiskAssessmentScheduleIds().contains(riskAssessmentScheduleId)) {
            return;
        }

        RiskAssessment updatedRiskAssessment = getUpdatedRiskAssessment(existingRiskAssessment.getId(), publisherId);

        List<String> existingRiskAssessmentScheduleIdList = updatedRiskAssessment.getRiskAssessmentScheduleIds();

        existingRiskAssessmentScheduleIdList.add(riskAssessmentScheduleId);

        updatedRiskAssessment.setRiskAssessmentScheduleIds(existingRiskAssessmentScheduleIdList);

        try {
            riskAssessmentRepository.save(updatedRiskAssessment);
            LOGGER.info("Risk assessment: " + updatedRiskAssessment.toString() + " saved in risk assessment repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public RiskAssessment deleteRiskAssessment(String id, String publisherId) {
        RiskAssessment riskAssessmentToDelete = checkRiskAssessmentExists(id);
        for (String riskAssessmentScheduleId : riskAssessmentToDelete.getRiskAssessmentScheduleIds()) {
            riskAssessmentScheduleService.deleteRiskAssessmentSchedule(riskAssessmentScheduleId, publisherId);
        }

        riskAssessmentRepository.deleteById(id);
        LOGGER.info("Risk assessment: " + riskAssessmentToDelete.toString() + " successfully fetched and deleted from risk assessment repository");
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

            for (String riskAssessmentScheduleId : riskAssessmentToDelete.getRiskAssessmentScheduleIds()) {
                riskAssessmentScheduleService.deleteRiskAssessmentSchedule(riskAssessmentScheduleId, publisherId);
            }

            try {
                riskAssessmentRepository.deleteById(riskAssessmentToDelete.getId());
                LOGGER.info("Risk assessment: " + riskAssessmentToDelete.toString() + " successfully fetched and deleted from risk assessment repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    public void removeDeletedRiskAssessmentScheduleFromRiskAssessment(String riskAssessmentId, String riskAssessmentScheduleId, String publisherId) {
        RiskAssessment existingRiskAssessment = checkRiskAssessmentExists(riskAssessmentId);

        RiskAssessment updatedRiskAssessment = getUpdatedRiskAssessment(existingRiskAssessment.getId(), publisherId);

        List<String> updatedRiskAssessmentScheduleIds = new ArrayList<>(updatedRiskAssessment.getRiskAssessmentScheduleIds());

        updatedRiskAssessmentScheduleIds.add(riskAssessmentScheduleId);

        updatedRiskAssessment.setRiskAssessmentScheduleIds(updatedRiskAssessmentScheduleIds);

        try {
            riskAssessmentRepository.save(updatedRiskAssessment);
            LOGGER.info("Risk assessment: " + updatedRiskAssessment.toString() + " updated and saved in risk assessment repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
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

    public List<RiskAssessment> getAllRiskAssessmentsById(List<String> riskAssessmentIdList) {
        return riskAssessmentRepository.findRiskAssessmentsById(riskAssessmentIdList);
    }

    public String getCreatedRiskAssessmentMessage() {
        return "CREATED RISK ASSESSMENT";
    }

    public String getUpdatedRiskAssessmentMessage() {
        return "UPDATED RISK ASSESSMENT";
    }
}
