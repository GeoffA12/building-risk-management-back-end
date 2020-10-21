package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.user.SiteAdmin;
import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
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

    private static Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

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

}
