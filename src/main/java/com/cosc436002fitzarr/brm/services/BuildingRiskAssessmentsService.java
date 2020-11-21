package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.BuildingRiskAssessments;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.input.CreateBuildingRiskAssessmentsInput;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import com.cosc436002fitzarr.brm.models.riskassessment.input.CreateRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.riskassessment.input.GetAllRiskAssessmentsBySiteInput;
import com.cosc436002fitzarr.brm.models.riskassessment.input.UpdateRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.WorkplaceHealthSafetyMember;
import com.cosc436002fitzarr.brm.repositories.BuildingRiskAssessmentsRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BuildingRiskAssessmentsService {
    @Autowired
    public BuildingRiskAssessmentsRepository buildingRiskAssessmentsRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(BuildingRiskAssessmentsService.class);

    public BuildingRiskAssessments createBuildingRiskAssessments(CreateBuildingRiskAssessmentsInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<EntityTrail> entityTrail = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedBuildingRiskAssessmentsMessage()));

        BuildingRiskAssessments buildingRiskAssessmentForPersistence = new BuildingRiskAssessments(
                id,
                currentTime,
                currentTime,
                entityTrail,
                input.getPublisherId(),
                input.getRiskAssessmentIds(),
                input.getBuildingId(),
                input.getTitle(),
                input.getDescription()
        );

        try {
            buildingRiskAssessmentsRepository.save(buildingRiskAssessmentForPersistence);
            LOGGER.info("Persisted new building risk assessment: " + buildingRiskAssessmentForPersistence.toString() + " to the building risk assessments repository");
        } catch(Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }
        return buildingRiskAssessmentForPersistence;
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
                updatedRiskAssessment.getWorkOrder(),
                input.getTitle(),
                input.getTaskDescription(),
                input.getHazards(),
                input.getScreeners(),
                updatedRiskAssessment.getBuildingId(),
                updatedRiskAssessment.getStatus(),
                updatedRiskAssessment.getDueDate(),
                updatedRiskAssessment.getRiskLevel(),
                updatedRiskAssessment.getSiteMaintenanceAssociateIds()
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

        List<WorkplaceHealthSafetyMember> filteredWorkplaceHealthSafetyMembers = workplaceHealthSafetyMemberService.getWorkplaceHealthSafetyMembersByUserIdAndSite(input.getAssociatedSiteIds());
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
        Optional<RiskAssessment> potentialRiskAssessment = riskAssessmentRepository.findById(id);
        Boolean riskAssessmentIsPresent = potentialRiskAssessment.isPresent();
        if (!riskAssessmentIsPresent) {
            LOGGER.info("Risk assessment with id: " + id + " not found in the risk assessment repository!");
            throw new EntityNotFoundException("Risk assessment with id: " + id + " not found in the risk assessment repository!");
        }
        else {
            riskAssessmentRepository.deleteById(id);
            LOGGER.info("Risk assessment: " + potentialRiskAssessment.get().toString() + " successfully fetched and deleted from risk assessment repository");
            RiskAssessment deletedRiskAssessment = potentialRiskAssessment.get();
            workplaceHealthSafetyMemberService.removeRiskAssessmentIdFromWorkplaceHealthSafetyMemberIdList(id, publisherId);
            return deletedRiskAssessment;
        }
    }

    public String getCreatedBuildingRiskAssessmentsMessage() {
            return "CREATED BUILDING RISK ASSESSMENT";
    }

    public String getUpdatedBuildingRiskAssessmentsMessage() {
        return "UPDATED BUILDING RISK ASSESSMENT";
    }
}
