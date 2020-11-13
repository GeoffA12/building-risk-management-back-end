package com.cosc436002fitzarr.brm.services;


import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RiskAssessmentService {
    @Autowired
    public RiskAssessmentRepository riskAssessmentRepository;
    @Autowired
    public WorkplaceHealthSafetyMemberService workplaceHealthSafetyMemberService;

    private static Logger LOGGER = LoggerFactory.getLogger(RiskAssessmentService.class);

    public RiskAssessment createRiskAssessment(CreateRiskAssessmentInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<EntityTrail> entityTrail = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedRiskAssessmentMessage()));

        RiskAssessment riskAssessmentForPersistence = new RiskAssessment(
            id,
            currentTime,
            currentTime,
            entityTrail,
            input.getPublisherId(),
            input.getWorkOrder(),
            input.getTitle(),
            input.getTaskDescription(),
            input.getHazards(),
            input.getScreeners(),
            input.getSiteIds()
        );

        try {
            riskAssessmentRepository.save(riskAssessmentForPersistence);
            LOGGER.info("Persisted new risk assessment: " + riskAssessmentForPersistence.toString() + " to the risk assessment repository");
            // TODO: Same comment as in the delete API. Since we have a property riskAssessmentsFiledIds in the WHS Member User Class, we need to update their
            // TODO: property in this API. You can create a new internal API in the whsMemberService file 'attachNewRiskAssessmentToWhsMember' which will
            // TODO: update that whs member's riskAssessmentFiled list, and then save that updated whs member to their repository.
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
            updatedRiskAssessment.getPublisherId(),
            input.getWorkOrder(),
            input.getTitle(),
            input.getTaskDescription(),
            input.getHazards(),
            input.getScreeners(),
            input.getSiteIds()
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

        List<WorkplaceHealthSafetyMember> filteredWorkplaceHealthSafetyMembers = workplaceHealthSafetyMemberService.getWorkplaceHealthSafetyMembersByUserIdAndSite(input.getUserId(), input.getAssociatedSiteIds());
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
            // TODO: Remember that we're storing a riskAssessmentFiledIds property in each whsmember. When someone deletes a risk assessment we should update the user
            // TODO: who submitted this risk assessments ID list so that it no longer contains the id of the deleted risk assessment.
            // TODO: Get the whs member using the publisherId passed into this API. You'll need to write a service function in the whsmemberservice file which
            // TODO: will update the whs member's riskAssessmentFiledId's list (it should remove the id from their list and then save that update)
            workplaceHealthSafetyMemberService.removeRiskAssessmentIdFromWorkplaceHealthSafetyMemberIdList(id, publisherId);
            return deletedRiskAssessment;
        }
    }

    public String getCreatedRiskAssessmentMessage() {
        return "CREATED RISK ASSESSMENT";
    }

    public String getUpdatedRiskAssessmentMessage() {
        return "UPDATED RISK ASSESSMENT";
    }
}
