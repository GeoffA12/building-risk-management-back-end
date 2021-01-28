package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.GetEntityBySiteInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessment.BuildingRiskAssessment;
import com.cosc436002fitzarr.brm.models.buildingriskassessment.input.CreateBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessment.input.GetBuildingRiskAssessmentsBySiteInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessment.input.UpdateBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import com.cosc436002fitzarr.brm.models.riskassessment.input.DeleteRiskAssessmentsInput;
import com.cosc436002fitzarr.brm.models.sitemaintenancemanager.SiteMaintenanceManager;
import com.cosc436002fitzarr.brm.repositories.BuildingRiskAssessmentRepository;
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
import javax.persistence.Query;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BuildingRiskAssessmentService {
    @Autowired
    public BuildingRiskAssessmentRepository buildingRiskAssessmentRepository;
    @Autowired
    public SiteMaintenanceManagerService siteMaintenanceManagerService;
    @Autowired
    public BuildingService buildingService;
    @Autowired
    public RiskAssessmentScheduleService riskAssessmentScheduleService;

    private static Logger LOGGER = LoggerFactory.getLogger(BuildingRiskAssessmentService.class);

    public BuildingRiskAssessment createBuildingRiskAssessment(CreateBuildingRiskAssessmentInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<EntityTrail> entityTrail = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedBuildingRiskAssessmentSystemComment()));

        BuildingRiskAssessment buildingRiskAssessmentForPersistence = new BuildingRiskAssessment(
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
            buildingRiskAssessmentRepository.save(buildingRiskAssessmentForPersistence);
            LOGGER.info("Successfully saved: " + buildingRiskAssessmentForPersistence.toString() + " to building" +
                    "risk assessment repository.");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        siteMaintenanceManagerService.attachNewBuildingRiskAssessmentToSiteMaintenanceManager(input.getPublisherId(), buildingRiskAssessmentForPersistence.getId());
        return buildingRiskAssessmentForPersistence;
    }

    public BuildingRiskAssessment getBuildingRiskAssessmentById(String id) {
        return checkBuildingRiskAssessmentExists(id);
    }

    public BuildingRiskAssessment checkBuildingRiskAssessmentExists(String id) {
        Optional<BuildingRiskAssessment> potentialBuildingRiskAssessment = buildingRiskAssessmentRepository.findById(id);
        Boolean buildingRiskAssessmentExists = potentialBuildingRiskAssessment.isPresent();
        if (!buildingRiskAssessmentExists) {
            LOGGER.info("Building risk assessment with id: " + id + " not found in the building risk assessment repository");
            throw new EntityNotFoundException();
        }
        BuildingRiskAssessment existingBuildingRiskAssessment = potentialBuildingRiskAssessment.get();
        return existingBuildingRiskAssessment;
    }

    public Map<String, Object> getBuildingRiskAssessmentsBySite(GetBuildingRiskAssessmentsBySiteInput input) {
        Sort sortProperty = Sort.by(input.getPageInput().getSortDirection(), input.getPageInput().getSortBy());
        Pageable pageInput = PageRequest.of(input.getPageInput().getPageNo().intValue(), input.getPageInput().getPageSize().intValue(), sortProperty);

        Page<BuildingRiskAssessment> sortedBuildingRiskAssessmentsInPage = buildingRiskAssessmentRepository.findAll(pageInput);
        List<BuildingRiskAssessment> buildingRiskAssessmentsContent = sortedBuildingRiskAssessmentsInPage.getContent();

        List<SiteMaintenanceManager> authorizedSiteMaintenanceManagers = siteMaintenanceManagerService.getSiteMaintenanceManagersBySite(input.getGetEntityBySiteInput().getAssociatedSiteIds());

        List<String> bulkBuildingRiskAssessmentIdList = new ArrayList<>();

        for (SiteMaintenanceManager siteMaintenanceManager : authorizedSiteMaintenanceManagers) {
            bulkBuildingRiskAssessmentIdList.addAll(siteMaintenanceManager.getBuildingRiskAssessmentIdsFiled());
        }

        List<BuildingRiskAssessment> allAssessmentsAtSite = buildingRiskAssessmentRepository.getBuildingRiskAssessmentsBySite(bulkBuildingRiskAssessmentIdList);

        List<BuildingRiskAssessment> sortedRiskAssessmentAtSite = buildingRiskAssessmentsContent.stream()
                .filter(allAssessmentsAtSite::contains)
                .collect(Collectors.toList());

        return PageUtils.getBuildingRiskAssessmentMappingResponse(sortedRiskAssessmentAtSite);
    }

    public BuildingRiskAssessment updateBuildingRiskAssessment(UpdateBuildingRiskAssessmentInput input) {
        BuildingRiskAssessment existingBuildingRiskAssessment = checkBuildingRiskAssessmentExists(input.getId());

        BuildingRiskAssessment updatedBuildingRiskAssessment = getUpdatedBuildingRiskAssessment(existingBuildingRiskAssessment, input.getPublisherId());

        BuildingRiskAssessment updatedBuildingRiskAssessmentForPersistence = new BuildingRiskAssessment(
            updatedBuildingRiskAssessment.getId(),
            updatedBuildingRiskAssessment.getCreatedAt(),
            updatedBuildingRiskAssessment.getUpdatedAt(),
            updatedBuildingRiskAssessment.getEntityTrail(),
            updatedBuildingRiskAssessment.getPublisherId(),
            input.getRiskAssessmentIds(),
            input.getBuildingId(),
            input.getTitle(),
            input.getDescription()
        );

        try {
            buildingRiskAssessmentRepository.save(updatedBuildingRiskAssessmentForPersistence);
            LOGGER.info("Updated building risk assessment: " + updatedBuildingRiskAssessmentForPersistence.toString() + " to the" +
                    "building risk assessment repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedBuildingRiskAssessmentForPersistence;
    }

    public BuildingRiskAssessment getUpdatedBuildingRiskAssessment(BuildingRiskAssessment existingAssessment, String publisherId) {
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        List<EntityTrail> updatedEntityTrail = existingAssessment.getEntityTrail();

        updatedEntityTrail.add(new EntityTrail(currentTime, publisherId, getUpdatedBuildingRiskAssessmentSystemComment()));

        existingAssessment.setEntityTrail(updatedEntityTrail);

        existingAssessment.setUpdatedAt(currentTime);

        return existingAssessment;
    }

    // TODO: This needs to be refactored so that we get a publisherID as input and use it in the risk assessment service and building service calls
    public BuildingRiskAssessment deleteBuildingRiskAssessment(String id, String publisherId) {
        BuildingRiskAssessment buildingRiskAssessmentToDelete = checkBuildingRiskAssessmentExists(id);
        siteMaintenanceManagerService.removeBuildingRiskAssessmentFromList(publisherId, buildingRiskAssessmentToDelete.getId());
        buildingService.removeBuildingRiskAssessmentFromBuilding(buildingRiskAssessmentToDelete.getBuildingId(), buildingRiskAssessmentToDelete.getId(), publisherId);
        riskAssessmentScheduleService.deleteRiskAssessmentSchedulesFromDeletedBuildingRiskAssessment(buildingRiskAssessmentToDelete.getId(), publisherId);
        try {
            buildingRiskAssessmentRepository.deleteById(buildingRiskAssessmentToDelete.getId());
            LOGGER.info("Successfully deleted: " + buildingRiskAssessmentToDelete.toString() + " from the" +
                    " building risk assessment repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return buildingRiskAssessmentToDelete;
    }

    public void deleteBuildingRiskAssessments(List<String> buildingRiskAssessmentIds, String publisherId) {
        for (String buildingRiskAssessmentId : buildingRiskAssessmentIds) {
           deleteBuildingRiskAssessment(buildingRiskAssessmentId, publisherId);
        }
    }

    public String getCreatedBuildingRiskAssessmentSystemComment() {
        return "CREATED BUILDING RISK ASSESSMENT";
    }

    public String getUpdatedBuildingRiskAssessmentSystemComment() {
        return "UPDATED BUILDING RISK ASSESSMENT";
    }

}
