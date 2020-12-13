package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.GetEntityBySiteInput;
import com.cosc436002fitzarr.brm.models.building.Building;
import com.cosc436002fitzarr.brm.models.building.input.CreateBuildingInput;
import com.cosc436002fitzarr.brm.models.building.input.UpdateBuildingInput;
import com.cosc436002fitzarr.brm.repositories.BuildingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class BuildingService {
    @Autowired
    public BuildingRepository buildingRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(BuildingService.class);

    public Building createBuilding(CreateBuildingInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<EntityTrail> entityTrail = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedBuildingMessage()));
        List<String> buildingRiskAssessmentIds = new ArrayList<>();

        Building buildingForPersistence = new Building(
                id,
                currentTime,
                currentTime,
                entityTrail,
                input.getPublisherId(),
                input.getName(),
                input.getCode(),
                input.getSiteId(),
                buildingRiskAssessmentIds,
                input.getAddress()
        );

        try {
            buildingRepository.save(buildingForPersistence);
            LOGGER.info("Persisted new building: " + buildingForPersistence.toString() + " to the building repository");
        } catch(Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }
        return buildingForPersistence;
    }

    public Building updateBuilding(UpdateBuildingInput input) {
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        Building updatedBuilding = getUpdatedBuilding(input.getId(), input.getPublisherId());

        Building updatedBuildingForPersistence = new Building(
                updatedBuilding.getId(),
                updatedBuilding.getCreatedAt(),
                currentTime,
                updatedBuilding.getEntityTrail(),
                input.getPublisherId(),
                input.getName(),
                input.getCode(),
                input.getSiteId(),
                updatedBuilding.getBuildingRiskAssessmentIds(),
                input.getAddress()
        );

        try {
            buildingRepository.save(updatedBuildingForPersistence);
            LOGGER.info("Building " + updatedBuildingForPersistence.toString() + " saved in building repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedBuildingForPersistence;
    }

    public Building getUpdatedBuilding(String existingBuildingId, String publisherId) {
        Building existingBuilding = checkBuildingExists(existingBuildingId);

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, publisherId, getUpdatedBuildingMessage());

        List<EntityTrail> existingTrail = existingBuilding.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        existingBuilding.setEntityTrail(updatedTrail);

        existingBuilding.setUpdatedAt(currentTime);

        return existingBuilding;
    }

    public Building checkBuildingExists(String id) {
        Optional<Building> potentialBuilding = buildingRepository.findById(id);
        if (!potentialBuilding.isPresent()) {
            LOGGER.info("Building with id: " + id + " not found in the building repository!");
            throw new EntityNotFoundException("Building with id: " + id + " not found in the building repository!");
        }
        LOGGER.info("Building: " + potentialBuilding.get().toString() + " successfully fetched from building repository");
        return potentialBuilding.get();
    }

    public Building getById(String id) {
        return checkBuildingExists(id);
    }

    public List<Building> getAllBuildingsBySite(GetEntityBySiteInput input) {
        List<String> allSiteIds = input.getAssociatedSiteIds();
        List<Building> matchingBuildingsBySiteId = new ArrayList<>();

        for (String siteId : allSiteIds) {
            matchingBuildingsBySiteId.addAll(buildingRepository.findBuildingsBySiteId(siteId));
        }
        return matchingBuildingsBySiteId;
    }

    public void removeBuildingRiskAssessmentFromBuilding(String buildingId, String buildingRiskAssessmentId, String publisherId) {
        Building existingBuilding = checkBuildingExists(buildingId);

        Building updatedBuilding = getUpdatedBuilding(existingBuilding.getId(), publisherId);

        List<String> existingBuildingRiskAssessmentIds = updatedBuilding.getBuildingRiskAssessmentIds();

        existingBuildingRiskAssessmentIds.remove(buildingRiskAssessmentId);

        updatedBuilding.setBuildingRiskAssessmentIds(existingBuildingRiskAssessmentIds);

        try {
            buildingRepository.save(updatedBuilding);
            LOGGER.info("Building: " + updatedBuilding.toString() + " successfully saved in building repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public String getCreatedBuildingMessage() {
        return "CREATED BUILDING";
    }

    public String getUpdatedBuildingMessage() {
        return "UPDATED BUILDING";
    }
}
