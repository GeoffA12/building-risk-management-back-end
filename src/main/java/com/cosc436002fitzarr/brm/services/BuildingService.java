package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.building.Building;
import com.cosc436002fitzarr.brm.models.building.input.CreateBuildingInput;
import com.cosc436002fitzarr.brm.models.building.input.GetAllBuildingsBySiteInput;
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
        Optional<Building> potentialBuilding = buildingRepository.findById(existingBuildingId);
        if (!potentialBuilding.isPresent()) {
            LOGGER.info("Building with id: " + existingBuildingId + " not found in building repository");
            throw new EntityNotFoundException();
        }

        Building existingBuilding = potentialBuilding.get();

        LOGGER.info("Successfully retrieved building: " + existingBuilding.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, publisherId, getUpdatedBuildingMessage());

        List<EntityTrail> existingTrail = existingBuilding.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        existingBuilding.setEntityTrail(updatedTrail);

        return existingBuilding;
    }

    public Building getById(String id) {
        Optional<Building> potentialBuilding = buildingRepository.findById(id);
        Boolean buildingIsPresent = potentialBuilding.isPresent();
        if (!buildingIsPresent) {
            LOGGER.info("Building with id: " + id + " not found in the building repository!");
            throw new EntityNotFoundException("Building with id: " + id + " not found in the building repository!");
        }
        else {
            LOGGER.info("Building: " + potentialBuilding.get().toString() + " successfully fetched from building repository");
            return potentialBuilding.get();
        }
    }

    public List<Building> getAllBuildingsBySite(GetAllBuildingsBySiteInput input) {
        List<Building> matchingBuildingsBySiteId;
        try {
            matchingBuildingsBySiteId = buildingRepository.findBuildingsBySiteId(input.getSiteId());
            LOGGER.info("Retrieved buildings from building repository: " + matchingBuildingsBySiteId.toString());
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return matchingBuildingsBySiteId;
    }

    public Building deleteBuilding(String id) {
        Optional<Building> potentialBuilding = buildingRepository.findById(id);
        Boolean buildingIsPresent = potentialBuilding.isPresent();
        if (!buildingIsPresent) {
            LOGGER.info("Building with id: " + id + " not found in the building repository!");
            throw new EntityNotFoundException("Building with id: " + id + " not found in the building repository!");
        }
        else {
            buildingRepository.deleteById(id);
            LOGGER.info("Building: " + potentialBuilding.get().toString() + " successfully fetched and deleted from building repository");
            Building deletedBuilding = potentialBuilding.get();
            return deletedBuilding;
        }
    }

    public String getCreatedBuildingMessage() {
        return "CREATED BUILDING";
    }

    public String getUpdatedBuildingMessage() {
        return "UPDATED BUILDING";
    }
}
