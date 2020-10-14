package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.user.SiteAdmin;
import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SiteAdminUserService {
    @Autowired
    public UserRepository userRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

    public User createSiteAdmin(CreateUserInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        List<EntityTrail> entityTrail = new ArrayList<>();
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
                input.getHashPassword()
        );

        userRepository.save(siteAdminForPersistence);
        LOGGER.info("Persisted: " + siteAdminForPersistence.toString() + " to the user repository");
        return siteAdminForPersistence;
    }

    public User authenticateUserLogin(User requestBody) {
        // Default example matcher used here. If we send in a requestBody where { userName: "John", hashPassword: "123Haha" },
        // the example matcher will use an AND operator between the two attributes by default, which is the functionality we want here.
        Example<User> user = Example.of(requestBody);
        // This line will look in the userRepository for the 'user' example matcher defined above. I.E. we're telling Mongo to query all user records
        // where { userName: "John", hashPassword: "123Haha" }
        Optional<User> possibleUser = userRepository.findOne(user);
        User userProfile;
        if (possibleUser.isPresent()) {
            User actualUser = possibleUser.get();
            userProfile = userRepository.findByUsername(actualUser.getUsername());
        } else {
            userProfile = null;
        }
        return userProfile;
    }



    public String getCreatedSiteAdminMessage() {
        return "CREATED SITE ADMIN USER";
    }

}
