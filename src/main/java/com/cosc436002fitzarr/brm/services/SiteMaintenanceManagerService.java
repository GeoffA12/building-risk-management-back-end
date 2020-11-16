package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
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

    private static Logger LOGGER = LoggerFactory.getLogger(SiteMaintenanceManagerService.class);

    public SiteMaintenanceManager createSiteMaintenanceManager(CreateUserInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String authToken = UserService.generateNewUserToken();
        String hashedPassword = UserService.createHashedPassword(input.getPassword());

        List<EntityTrail> entityTrail = new ArrayList<>();
        List<String> associatedSiteIds = new ArrayList<>();
        associatedSiteIds.add(input.getSiteId());
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
            siteMaintenanceAssociateIds
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

    public SiteMaintenanceManager updateSiteMaintenanceManager(UpdateUserInput input) {
        Optional<SiteMaintenanceManager> potentialSiteMaintenanceManager = siteMaintenanceManagerRepository.findById(input.getId());
        if (!potentialSiteMaintenanceManager.isPresent()) {
            throw new EntityNotFoundException("Site Maintenance Manager with id: " + input.getId() + " not found in site maintenance manager repository!");
        }

        SiteMaintenanceManager existingSiteMaintenanceManager = potentialSiteMaintenanceManager.get();
        LOGGER.info("Successfully retrieved site maintenance manager user: " + existingSiteMaintenanceManager.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        // TODO: Refactor the UpdateUserInput class so that a publisherId in the input object. Otherwise, we have no way of knowing the ID of whoever is updating this
        // specific site admin and can't update the Entity Trail accordingly.
        EntityTrail updateTrail = new EntityTrail(currentTime, input.getUserId(), getUpdatedSiteMaintenanceManagerSystemComment());

        List<EntityTrail> existingTrail = existingSiteMaintenanceManager.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        SiteMaintenanceManager updatedSiteMaintenanceManagerForPersistence = new SiteMaintenanceManager(
            existingSiteMaintenanceManager.getId(),
            currentTime,
            existingSiteMaintenanceManager.getCreatedAt(),
            updatedTrail,
            input.getUserId(),
            existingSiteMaintenanceManager.getSiteRole(),
            input.getFirstName(),
            input.getLastName(),
            input.getUsername(),
            input.getEmail(),
            input.getPhone(),
            existingSiteMaintenanceManager.getAuthToken(),
            existingSiteMaintenanceManager.getHashPassword(),
            input.getSiteIds(),
            existingSiteMaintenanceManager.getSiteMaintenanceAssociateIds()
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
        Optional<SiteMaintenanceManager> potentialSiteMaintenanceManager = siteMaintenanceManagerRepository.findById(id);
        Boolean siteMaintenanceAssociatePresent = potentialSiteMaintenanceManager.isPresent();
        if (!siteMaintenanceAssociatePresent) {
            LOGGER.info("Site maintenance manager with id: " + id + " not found in the site maintenance manager repository!");
            throw new EntityNotFoundException("Site maintenance manager with id: " + id + " not found in the site maintenance manager repository!");
        }
        else {
            siteMaintenanceManagerRepository.deleteById(id);
            userRepository.deleteById(id);
            LOGGER.info("Site maintenance manager: " + potentialSiteMaintenanceManager.get().toString() + " successfully fetched and deleted from site maintenance manager" +
                    " and user repositories");
            SiteMaintenanceManager siteMaintenanceManagerToDelete = potentialSiteMaintenanceManager.get();
            List<String> deletedAssociatedSiteIds = siteMaintenanceManagerToDelete.getAssociatedSiteIds();
            siteService.removeAssociatedSiteIdsFromSites(deletedAssociatedSiteIds, siteMaintenanceManagerToDelete.getId(), userId);
            return siteMaintenanceManagerToDelete;
        }
    }

    public void attachSiteMaintenanceAssociateIdToSiteMaintenanceManagerIdList(String siteMaintenanceAssociateId, String existingSiteMaintenanceManagerId) {
        // TODO: Put lines here in a common function getUpdatedEntitySetup, to cut down on duplicate code when we update an entity.
        Optional<SiteMaintenanceManager> potentialSiteMaintenanceManager = siteMaintenanceManagerRepository.findById(existingSiteMaintenanceManagerId);
        if (!potentialSiteMaintenanceManager.isPresent()) {
            throw new EntityNotFoundException("Site Maintenance Manager with id: " + existingSiteMaintenanceManagerId + " not found in site maintenance manager repository!");
        }

        SiteMaintenanceManager existingSiteMaintenanceManager = potentialSiteMaintenanceManager.get();
        LOGGER.info("Successfully retrieved site maintenance manager user: " + existingSiteMaintenanceManager.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        // TODO: Refactor the UpdateUserInput class so that a publisherId in the input object. Otherwise, we have no way of knowing the ID of whoever is updating this
        // specific site admin and can't update the Entity Trail accordingly.
        EntityTrail updateTrail = new EntityTrail(currentTime, existingSiteMaintenanceManager.getId(), getUpdatedSiteMaintenanceManagerSystemComment());

        List<EntityTrail> existingTrail = existingSiteMaintenanceManager.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        List<String> updatedSiteMaintenanceAssociateIds = new ArrayList<>(existingSiteMaintenanceManager.getSiteMaintenanceAssociateIds());

        updatedSiteMaintenanceAssociateIds.add(siteMaintenanceAssociateId);

        SiteMaintenanceManager updatedSiteMaintenanceManagerForPersistence = new SiteMaintenanceManager(
            existingSiteMaintenanceManager.getId(),
            existingSiteMaintenanceManager.getCreatedAt(),
            currentTime,
            updatedTrail,
            existingSiteMaintenanceManager.getId(),
            existingSiteMaintenanceManager.getSiteRole(),
            existingSiteMaintenanceManager.getFirstName(),
            existingSiteMaintenanceManager.getLastName(),
            existingSiteMaintenanceManager.getUsername(),
            existingSiteMaintenanceManager.getEmail(),
            existingSiteMaintenanceManager.getPhone(),
            existingSiteMaintenanceManager.getAuthToken(),
            existingSiteMaintenanceManager.getHashPassword(),
            existingSiteMaintenanceManager.getAssociatedSiteIds(),
            updatedSiteMaintenanceAssociateIds
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
    }

    public String getCreatedSiteMaintenanceManagerSystemComment() {
        return "CREATED SITE MAINTENANCE MANAGER";
    }

    public String getUpdatedSiteMaintenanceManagerSystemComment() {
        return "UPDATED SITE MAINTENANCE MANAGER";
    }
}
