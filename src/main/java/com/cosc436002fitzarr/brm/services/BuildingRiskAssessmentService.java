package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.GetEntityBySiteInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.BuildingRiskAssessment;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.input.CreateBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessments.input.UpdateBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.sitemaintenancemanager.SiteMaintenanceManager;
import com.cosc436002fitzarr.brm.repositories.BuildingRiskAssessmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class BuildingRiskAssessmentService {
    @Autowired
    public BuildingRiskAssessmentRepository buildingRiskAssessmentRepository;
    @Autowired
    public SiteMaintenanceManagerService siteMaintenanceManagerService;
    @Autowired
    public BuildingService buildingService;

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

    public List<BuildingRiskAssessment> getBuildingRiskAssessmentsBySite(GetEntityBySiteInput input) {
        List<SiteMaintenanceManager> authorizedSiteMaintenanceManagers = siteMaintenanceManagerService.getSiteMaintenanceManagersBySite(input.getAssociatedSiteIds());

        List<String> bulkBuildingRiskAssessmentIdList = new ArrayList<>();

        for (SiteMaintenanceManager siteMaintenanceManager : authorizedSiteMaintenanceManagers) {
            bulkBuildingRiskAssessmentIdList.addAll(siteMaintenanceManager.getBuildingRiskAssessmentIdsFiled());
        }

        List<BuildingRiskAssessment> allAssessmentsAtSite = buildingRiskAssessmentRepository.getBuildingRiskAssessmentsBySite(bulkBuildingRiskAssessmentIdList);

        return allAssessmentsAtSite;
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

    // TODO: In this API you still have to update the pointers we have in risk assessment, building, and site maintenance associate ID. For example,
    // TODO: If a building risk assessment is deleted, there is no way that the site maintenance associate will be assigned to risk assessments belonging to the building assessment
    public BuildingRiskAssessment deleteBuildingRiskAssessment(String id) {
        BuildingRiskAssessment buildingRiskAssessmentToDelete = checkBuildingRiskAssessmentExists(id);

        try {
            buildingRiskAssessmentRepository.deleteById(buildingRiskAssessmentToDelete.getId());
            LOGGER.info("Successfully deleted: " + buildingRiskAssessmentToDelete.toString() + " from the" +
                    " building risk assessment repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        String siteMaintenanceManagerToUpdateId = buildingRiskAssessmentToDelete.getPublisherId();
        siteMaintenanceManagerService.removeBuildingRiskAssessmentFromList(siteMaintenanceManagerToUpdateId, buildingRiskAssessmentToDelete.getId());
        buildingService.removeBuildingRiskAssessmentFromBuilding(buildingRiskAssessmentToDelete.getBuildingId(), buildingRiskAssessmentToDelete.getId(), siteMaintenanceManagerToUpdateId);
        return buildingRiskAssessmentToDelete;
    }

    public void deleteBuildingRiskAssessments(List<String> buildingRiskAssessmentIds) {
        for (String id : buildingRiskAssessmentIds) {
            BuildingRiskAssessment existingBuildingRiskAssessment = checkBuildingRiskAssessmentExists(id);

            try {
                buildingRiskAssessmentRepository.deleteById(existingBuildingRiskAssessment.getId());
                LOGGER.info("Successfully deleted: " + existingBuildingRiskAssessment.toString() + " from the" +
                        " building risk assessment repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    public String getCreatedBuildingRiskAssessmentSystemComment() {
        return "CREATED BUILDING RISK ASSESSMENT";
    }

    public String getUpdatedBuildingRiskAssessmentSystemComment() {
        return "UPDATED BUILDING RISK ASSESSMENT";
    }

}
