package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.RiskAssessmentSchedule;
import com.cosc436002fitzarr.brm.models.sitemaintenanceassociate.SiteMaintenanceAssociate;
import com.cosc436002fitzarr.brm.models.sitemaintenancemanager.SiteMaintenanceManager;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.repositories.SiteMaintenanceManagerRepository;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
import com.cosc436002fitzarr.brm.utils.UserAPIHelper;
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
public class SiteMaintenanceManagerService {
    @Autowired
    public SiteMaintenanceManagerRepository siteMaintenanceManagerRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SiteService siteService;
    @Autowired
    public BuildingRiskAssessmentService buildingRiskAssessmentService;
    @Autowired
    public SiteMaintenanceAssociateService siteMaintenanceAssociateService;
    @Autowired
    public RiskAssessmentScheduleService riskAssessmentScheduleService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteMaintenanceManagerService.class);

    public SiteMaintenanceManager createSiteMaintenanceManager(CreateUserInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String authToken = UserService.generateNewUserToken();
        String hashedPassword = UserService.createHashedPassword(input.getPassword());

        List<EntityTrail> entityTrail = new ArrayList<>();
        List<String> associatedSiteIds = new ArrayList<>();
        associatedSiteIds.add(input.getSiteId());

        List<String> riskAssessmentIdsFiled = new ArrayList<>();
        List<String> buildingIdsFiled = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, id, getCreatedSiteMaintenanceManagerSystemComment()));

        List<String> siteMaintenanceAssociateIds = new ArrayList<>();
        SiteMaintenanceManager siteMaintenanceManagerForPersistence = new SiteMaintenanceManager(
            id,
            currentTime,
            currentTime,
            entityTrail,
            id,
            input.getSiteRole(),
            input.getFirstName(),
            input.getLastName(),
            input.getUsername(),
            input.getEmail(),
            input.getPhone(),
            authToken,
            hashedPassword,
            associatedSiteIds,
            siteMaintenanceAssociateIds,
            riskAssessmentIdsFiled,
            buildingIdsFiled
        );

        try {
            siteMaintenanceManagerRepository.save(siteMaintenanceManagerForPersistence);
            LOGGER.info("Persisted new site admin: " + siteMaintenanceManagerForPersistence.toString() + " to the site admin repository");
            userRepository.save(siteMaintenanceManagerForPersistence);
            LOGGER.info("Persisted new site admin: " + siteMaintenanceManagerForPersistence.toString() + " to the user repository");
        } catch(Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }
        siteService.attachUserIdToUserIdList(associatedSiteIds, siteMaintenanceManagerForPersistence.getId());
        return siteMaintenanceManagerForPersistence;
    }

    public SiteMaintenanceManager getSiteMaintenanceManagerById(String id) {
        Optional<SiteMaintenanceManager> potentialSiteMaintenanceManager = siteMaintenanceManagerRepository.findById(id);
        Boolean siteMaintenanceManagerIsPresent = potentialSiteMaintenanceManager.isPresent();
        if (!siteMaintenanceManagerIsPresent) {
            LOGGER.info("Site maintenance manager with id: " + id + " not found in the site maintenance associate repository!");
            throw new EntityNotFoundException("Site maintenance associate with id: " + id + " not found in the site maintenance manager repository!");
        }
        else {
            LOGGER.info("Site maintenance manager: " + potentialSiteMaintenanceManager.get().toString() + " successfully fetched from site maintenance manager repository");
            return potentialSiteMaintenanceManager.get();
        }
    }

    public SiteMaintenanceManager getUpdatedSiteMaintenanceManager(String id, String publisherId) {
        SiteMaintenanceManager existingSiteMaintenanceManager = checkSiteMaintenanceManagerExists(id);

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        // TODO: Refactor the UpdateUserInput class so that a publisherId in the input object. Otherwise, we have no way of knowing the ID of whoever is updating this
        // specific site admin and can't update the Entity Trail accordingly.
        EntityTrail updateTrail = new EntityTrail(currentTime, publisherId, getUpdatedSiteMaintenanceManagerSystemComment());

        List<EntityTrail> existingTrail = existingSiteMaintenanceManager.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        existingSiteMaintenanceManager.setEntityTrail(updatedTrail);

        existingSiteMaintenanceManager.setUpdatedAt(currentTime);

        return existingSiteMaintenanceManager;
    }

    public SiteMaintenanceManager updateSiteMaintenanceManager(UpdateUserInput input) {
        SiteMaintenanceManager updatedSiteMaintenanceManager = getUpdatedSiteMaintenanceManager(input.getId(), input.getUserId());

        String passwordInput = input.getPassword() != null ? UserService.createHashedPassword(input.getPassword()) : updatedSiteMaintenanceManager.getHashPassword();

        SiteMaintenanceManager updatedSiteMaintenanceManagerForPersistence = new SiteMaintenanceManager(
            updatedSiteMaintenanceManager.getId(),
            updatedSiteMaintenanceManager.getCreatedAt(),
            updatedSiteMaintenanceManager.getUpdatedAt(),
            updatedSiteMaintenanceManager.getEntityTrail(),
            updatedSiteMaintenanceManager.getPublisherId(),
            updatedSiteMaintenanceManager.getSiteRole(),
            input.getFirstName(),
            input.getLastName(),
            input.getUsername(),
            input.getEmail(),
            input.getPhone(),
            updatedSiteMaintenanceManager.getAuthToken(),
            passwordInput,
            input.getSiteIds(),
            updatedSiteMaintenanceManager.getSiteMaintenanceAssociateIds(),
            updatedSiteMaintenanceManager.getBuildingRiskAssessmentIdsFiled(),
            updatedSiteMaintenanceManager.getBuildingIdsFiled()
        );

        try {
            siteMaintenanceManagerRepository.save(updatedSiteMaintenanceManagerForPersistence);
            LOGGER.info("Site maintenance manager: " + updatedSiteMaintenanceManagerForPersistence.toString() + " saved in site maintenance manager repository");
            userRepository.save(updatedSiteMaintenanceManagerForPersistence);
            LOGGER.info("Site maintenance manager: " + updatedSiteMaintenanceManagerForPersistence.toString() + " saved in site maintenance manager repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedSiteMaintenanceManagerForPersistence;
    }

    public SiteMaintenanceManager deleteSiteMaintenanceManager(String id, String userId) {
        UserAPIHelper.checkUserNotDeletingThemselves(id, userId);
        SiteMaintenanceManager siteMaintenanceManagerToDelete = checkSiteMaintenanceManagerExists(id);

        List<String> deletedAssociatedSiteIds = siteMaintenanceManagerToDelete.getAssociatedSiteIds();
        siteService.removeAssociatedSiteIdsFromSites(deletedAssociatedSiteIds, siteMaintenanceManagerToDelete.getId(), userId);
        List<String> siteMaintenanceAssociatesOfManager = siteMaintenanceManagerToDelete.getSiteMaintenanceAssociateIds();
        for (String siteMaintenanceAssociateId : siteMaintenanceAssociatesOfManager) {
            siteMaintenanceAssociateService.deleteSiteMaintenanceAssociate(siteMaintenanceAssociateId, userId);
        }

        buildingRiskAssessmentService.deleteBuildingRiskAssessments(siteMaintenanceManagerToDelete.getBuildingRiskAssessmentIdsFiled(), userId);

        siteMaintenanceManagerRepository.deleteById(siteMaintenanceManagerToDelete.getId());
        userRepository.deleteById(id);
        LOGGER.info("Site maintenance manager: " + siteMaintenanceManagerToDelete.toString() + " successfully fetched and deleted from site maintenance manager" +
                " and user repositories");
        return siteMaintenanceManagerToDelete;

    }

    public void attachSiteMaintenanceAssociateIdToSiteMaintenanceManagerIdList(String siteMaintenanceAssociateId, String existingSiteMaintenanceManagerId) {
        SiteMaintenanceManager existingSiteMaintenanceManager = getUpdatedSiteMaintenanceManager(existingSiteMaintenanceManagerId, siteMaintenanceAssociateId);

        List<String> updatedSiteMaintenanceAssociateIds = new ArrayList<>(existingSiteMaintenanceManager.getSiteMaintenanceAssociateIds());

        updatedSiteMaintenanceAssociateIds.add(siteMaintenanceAssociateId);

        existingSiteMaintenanceManager.setSiteMaintenanceAssociateIds(updatedSiteMaintenanceAssociateIds);

        try {
            siteMaintenanceManagerRepository.save(existingSiteMaintenanceManager);
            LOGGER.info("Site maintenance manager: " + existingSiteMaintenanceManager.toString() + " saved in site maintenance manager repository");
            userRepository.save(existingSiteMaintenanceManager);
            LOGGER.info("Site maintenance manager: " + existingSiteMaintenanceManager.toString() + " saved in site maintenance manager repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public SiteMaintenanceManager checkSiteMaintenanceManagerExists(String id) {
        Optional<SiteMaintenanceManager> potentialSiteMaintenanceManager = siteMaintenanceManagerRepository.findById(id);
        if (!potentialSiteMaintenanceManager.isPresent()) {
            LOGGER.info("Site maintenance manager with id: " + id + " not found in site maintenance manager repository");
            throw new EntityNotFoundException("Site maintenance manager with id: " + id + " not found in site maintenance manager repository");
        }
        return potentialSiteMaintenanceManager.get();
    }

    public void attachNewBuildingRiskAssessmentToSiteMaintenanceManager(String maintenanceManagerId, String buildingRiskAssessmentId) {
        SiteMaintenanceManager existingSiteMaintenanceManager = checkSiteMaintenanceManagerExists(maintenanceManagerId);

        SiteMaintenanceManager updatedSiteMaintenanceManager = getUpdatedSiteMaintenanceManager(existingSiteMaintenanceManager.getId(), maintenanceManagerId);

        LOGGER.info(updatedSiteMaintenanceManager.toString());

        List<String> updatedBuildingRiskAssessmentIdsFiled;

        if (updatedSiteMaintenanceManager.getBuildingRiskAssessmentIdsFiled() != null) {
            updatedBuildingRiskAssessmentIdsFiled = new ArrayList<>(updatedSiteMaintenanceManager.getBuildingRiskAssessmentIdsFiled());
        } else {
            updatedBuildingRiskAssessmentIdsFiled = new ArrayList<>();
        }

        updatedBuildingRiskAssessmentIdsFiled.add(buildingRiskAssessmentId);

        updatedSiteMaintenanceManager.setBuildingRiskAssessmentIdsFiled(updatedBuildingRiskAssessmentIdsFiled);

        try {
            siteMaintenanceManagerRepository.save(updatedSiteMaintenanceManager);
            LOGGER.info("Site maintenance manager: " + updatedSiteMaintenanceManager.toString() + " saved in the " +
                    "site maintenance manager repository");
            userRepository.save(updatedSiteMaintenanceManager);
            LOGGER.info("User: " + updatedSiteMaintenanceManager.toString() + " updated in the user repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public void removeBuildingRiskAssessmentFromList(String siteMaintenanceManagerId, String buildingRiskAssessmentId) {
        SiteMaintenanceManager existingSiteMaintenanceManager = checkSiteMaintenanceManagerExists(siteMaintenanceManagerId);

        SiteMaintenanceManager updatedSiteMaintenanceManager = getUpdatedSiteMaintenanceManager(existingSiteMaintenanceManager.getId(), siteMaintenanceManagerId);

        List<String> buildingRiskAssessments = updatedSiteMaintenanceManager.getBuildingRiskAssessmentIdsFiled();

        buildingRiskAssessments.remove(buildingRiskAssessmentId);

        updatedSiteMaintenanceManager.setBuildingRiskAssessmentIdsFiled(buildingRiskAssessments);

        try {
            siteMaintenanceManagerRepository.save(updatedSiteMaintenanceManager);
            LOGGER.info("Site maintenance manager: " + existingSiteMaintenanceManager.toString() + " saved in site maintenance manager repository");
            userRepository.save(updatedSiteMaintenanceManager);
            LOGGER.info("Site maintenance manager: " + existingSiteMaintenanceManager.toString() + " saved in site maintenance manager repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public List<SiteMaintenanceManager> getSiteMaintenanceManagersBySite(List<String> associatedSiteIds) {
        return siteMaintenanceManagerRepository.getSiteMaintenanceManagersBySite(associatedSiteIds);
    }

    public void removeDeletedSiteMaintenanceAssociateFromManagerIdList(String siteMaintenanceManagerId, String publisherId) {
        SiteMaintenanceManager existingSiteMaintenanceManager = checkSiteMaintenanceManagerExists(siteMaintenanceManagerId);

        SiteMaintenanceManager updatedSiteMaintenanceManager = getUpdatedSiteMaintenanceManager(existingSiteMaintenanceManager.getId(), publisherId);

        List<String> existingMaintenanceAssociatesManagedList = updatedSiteMaintenanceManager.getSiteMaintenanceAssociateIds();

        existingMaintenanceAssociatesManagedList.remove(siteMaintenanceManagerId);

        updatedSiteMaintenanceManager.setSiteMaintenanceAssociateIds(existingMaintenanceAssociatesManagedList);

        try {
            siteMaintenanceManagerRepository.save(updatedSiteMaintenanceManager);
            LOGGER.info("Site maintenance manager: " + existingSiteMaintenanceManager.toString() + " saved in site maintenance manager repository");
            userRepository.save(updatedSiteMaintenanceManager);
            LOGGER.info("Site maintenance manager: " + existingSiteMaintenanceManager.toString() + " saved in site maintenance manager repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesOfSiteMaintenanceAssociatesOfManager(String id, Boolean activeSchedules) {
        SiteMaintenanceManager existingSiteMaintenanceManager = checkSiteMaintenanceManagerExists(id);

        List<SiteMaintenanceAssociate> siteMaintenanceAssociatesOfManager = siteMaintenanceAssociateService.getSiteMaintenanceAssociatesBySiteMaintenanceAssociateIdList(existingSiteMaintenanceManager.getSiteMaintenanceAssociateIds());

        Set<String> riskAssessmentScheduleIds = new HashSet<>();

        for (SiteMaintenanceAssociate siteMaintenanceAssociate : siteMaintenanceAssociatesOfManager) {
            riskAssessmentScheduleIds.addAll(siteMaintenanceAssociate.getAssignedRiskAssessmentScheduleIds());
        }

        List<String> uniqueRiskAssessmentScheduleIds = new ArrayList<>(riskAssessmentScheduleIds);

        return riskAssessmentScheduleService.getRiskAssessmentSchedulesOfSiteMaintenanceManager(uniqueRiskAssessmentScheduleIds, activeSchedules);
    }

    public String getCreatedSiteMaintenanceManagerSystemComment() {
        return "CREATED SITE MAINTENANCE MANAGER";
    }

    public String getUpdatedSiteMaintenanceManagerSystemComment() {
        return "UPDATED SITE MAINTENANCE MANAGER";
    }
}
