package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.site.Site;
import com.cosc436002fitzarr.brm.models.site.input.UpdateSiteInput;
import com.cosc436002fitzarr.brm.models.user.SiteAdmin;
import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SiteAdminUserService {
    @Autowired
    public UserRepository userRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteAdminUserService.class);

    public User createSiteAdmin(CreateUserInput input, String authToken) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        List<EntityTrail> entityTrail = new ArrayList<>();
        List<String> associatedSiteIds = new ArrayList<>();
        associatedSiteIds.add(input.getSiteId());
        entityTrail.add(new EntityTrail(currentTime, id, getCreatedSiteAdminMessage()));
        User siteAdminForPersistence = new SiteAdmin(
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
            input.getPassword(),
            associatedSiteIds
        );

        userRepository.save(siteAdminForPersistence);
        LOGGER.info("Persisted new site admin: " + siteAdminForPersistence.toString() + " to the user repository");
        return siteAdminForPersistence;
    }

    public String getCreatedSiteAdminMessage() {
        return "CREATED SITE ADMIN USER";
    }

    public User updateSiteAdmin(UpdateUserInput input) {
        User existingSiteAdmin;
        try {
            existingSiteAdmin = userRepository.getById(input.getId());
            LOGGER.info("Successfully retrieved site admin user: " + existingSiteAdmin.toString() + " out of repository to update.");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, existingSiteAdmin.getId(), getUpdatedSiteAdminMessage());

        List<EntityTrail> existingTrail = existingSiteAdmin.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        User updatedSiteAdminForPersistence = new SiteAdmin(
                existingSiteAdmin.getId(),
                currentTime,
                existingSiteAdmin.getCreatedAt(),
                updatedTrail,
                existingSiteAdmin.getId(),
                input.getSiteRole(),
                input.getFirstName(),
                input.getLastName(),
                input.getUsername(),
                input.getEmail(),
                input.getPhone(),
                input.getAuthToken(),
                input.getHashPassword(),
                existingSiteAdmin.getAssociatedSiteIds()
        );

        try {
            userRepository.save(updatedSiteAdminForPersistence);
            LOGGER.info("User " + updatedSiteAdminForPersistence.toString() + " saved in user repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedSiteAdminForPersistence;
    }

    public String getUpdatedSiteAdminMessage() {
        return "UPDATED SITE ADMIN USER";
    }

    public List<User> getAllSiteAdmins(SiteRole siteRole) {
        return userRepository.findBySiteRole(siteRole);
    }

}
