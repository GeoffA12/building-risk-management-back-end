package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.site.Site;
import com.cosc436002fitzarr.brm.models.siteadmin.SiteAdmin;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.repositories.SiteAdminRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SiteAdminService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SiteAdminRepository siteAdminRepository;
    @Autowired
    public SiteService siteService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteAdminService.class);

    public SiteAdmin createSiteAdmin(CreateUserInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String authToken = UserService.generateNewUserToken();
        String hashedPassword = UserService.createHashedPassword(input.getPassword());
        List<EntityTrail> entityTrail = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, id, getCreatedSiteAdminMessage()));
        List<Site> allSitesInRepository = siteService.getAllSites();
        List<String> associatedSiteIds = new ArrayList<>();
        for (Site site : allSitesInRepository) {
            associatedSiteIds.add(site.getId());
        }

        SiteAdmin siteAdminForPersistence = new SiteAdmin(
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
            associatedSiteIds
        );

        try {
            siteAdminRepository.save(siteAdminForPersistence);
            LOGGER.info("Persisted new site admin: " + siteAdminForPersistence.toString() + " to the site admin repository");
            userRepository.save(siteAdminForPersistence);
            LOGGER.info("Persisted new site admin: " + siteAdminForPersistence.toString() + " to the user repository");
        } catch(Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }
        siteService.attachUserIdToUserIdList(associatedSiteIds, siteAdminForPersistence.getId());
        return siteAdminForPersistence;
    }

    public SiteAdmin updateSiteAdmin(UpdateUserInput input) {
        SiteAdmin updatedSiteAdmin = getUpdatedSiteAdmin(input.getId(), input.getUserId());
        String passwordInput = input.getPassword() != null ? UserService.createHashedPassword(input.getPassword()) : updatedSiteAdmin.getHashPassword();

        SiteAdmin updatedSiteAdminForPersistence = new SiteAdmin(
            updatedSiteAdmin.getId(),
            updatedSiteAdmin.getCreatedAt(),
            updatedSiteAdmin.getUpdatedAt(),
            updatedSiteAdmin.getEntityTrail(),
            updatedSiteAdmin.getPublisherId(),
            updatedSiteAdmin.getSiteRole(),
            input.getFirstName(),
            input.getLastName(),
            input.getUsername(),
            input.getEmail(),
            input.getPhone(),
            updatedSiteAdmin.getAuthToken(),
            passwordInput,
            input.getSiteIds()
        );

        try {
            siteAdminRepository.save(updatedSiteAdminForPersistence);
            LOGGER.info("Site admin " + updatedSiteAdminForPersistence.toString() + " saved in site admin repository");
            userRepository.save(updatedSiteAdminForPersistence);
            LOGGER.info("Site admin: " + updatedSiteAdminForPersistence.toString() + " saved in user repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedSiteAdminForPersistence;
    }

    public SiteAdmin checkSiteAdminExists(String id) {
        Optional<SiteAdmin> potentialSiteAdmin = siteAdminRepository.findById(id);
        if (!potentialSiteAdmin.isPresent()) {
            LOGGER.info("Site admin with id: " + id + " not found in site admin repository");
            throw new EntityNotFoundException("Site admin with id: " + id + " not found in site admin repository");
        }
        return potentialSiteAdmin.get();
    }

    public SiteAdmin getUpdatedSiteAdmin(String existingSiteAdminId, String userId) {
        SiteAdmin existingSiteAdmin = checkSiteAdminExists(existingSiteAdminId);

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, userId, getUpdatedSiteAdminMessage());

        List<EntityTrail> existingTrail = existingSiteAdmin.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        existingSiteAdmin.setEntityTrail(updatedTrail);

        existingSiteAdmin.setUpdatedAt(currentTime);

        return existingSiteAdmin;
    }

    public SiteAdmin getById(String id) {
        Optional<SiteAdmin> potentialSiteAdmin = siteAdminRepository.findById(id);
        Boolean siteAdminIsPresent = potentialSiteAdmin.isPresent();
        if (!siteAdminIsPresent) {
            LOGGER.info("Site admin with id: " + id + " not found in the site admin repository!");
            throw new EntityNotFoundException("Site admin with id: " + id + " not found in the site admin repository!");
        }
        else {
            LOGGER.info("Site admin: " + potentialSiteAdmin.get().toString() + " successfully fetched from site admin repository");
            return potentialSiteAdmin.get();
        }
    }

    public SiteAdmin deleteSiteAdmin(String id, String userId) {
        UserAPIHelper.checkUserNotDeletingThemselves(id, userId);
        Optional<SiteAdmin> potentialSiteAdmin = siteAdminRepository.findById(id);
        Boolean siteAdminIsPresent = potentialSiteAdmin.isPresent();
        if (!siteAdminIsPresent) {
            LOGGER.info("Site admin with id: " + id + " not found in the site admin repository!");
            throw new EntityNotFoundException("Site admin with id: " + id + " not found in the site admin repository!");
        }
        else {
            siteAdminRepository.deleteById(id);
            userRepository.deleteById(id);
            LOGGER.info("Site admin: " + potentialSiteAdmin.get().toString() + " successfully fetched and deleted from site admin and user repositories");
            SiteAdmin deletedSiteAdmin = potentialSiteAdmin.get();
            siteService.removeAssociatedSiteIdsFromSites(deletedSiteAdmin.getAssociatedSiteIds(), deletedSiteAdmin.getId(), userId);
            return deletedSiteAdmin;
        }
    }

    public void attachNewSiteToAllSiteAdmins(String siteId, String userId) {
        List<SiteAdmin> allSiteAdmins = siteAdminRepository.findAll();
        for (SiteAdmin siteAdmin : allSiteAdmins) {
            SiteAdmin updatedSiteAdmin = getUpdatedSiteAdmin(siteAdmin.getId(), userId);
            List<String> updatedAssociatedSiteIds = updatedSiteAdmin.getAssociatedSiteIds();
            updatedAssociatedSiteIds.add(siteId);
            updatedSiteAdmin.setAssociatedSiteIds(updatedAssociatedSiteIds);

            try {
                siteAdminRepository.save(updatedSiteAdmin);
                LOGGER.info("Site admin: " + updatedSiteAdmin.toString() + " successfully saved in site admin repository");
                userRepository.save(updatedSiteAdmin);
                LOGGER.info("User: " + updatedSiteAdmin.toString() + " successfully saved in user repository");
            } catch (Exception e ) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    public String getCreatedSiteAdminMessage() {
        return "CREATED SITE ADMIN USER";
    }

    public String getUpdatedSiteAdminMessage() {
        return "UPDATED SITE ADMIN USER";
    }
}
