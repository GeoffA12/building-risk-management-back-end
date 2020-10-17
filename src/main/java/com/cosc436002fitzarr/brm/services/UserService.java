package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.site.Site;
import com.cosc436002fitzarr.brm.models.site.input.ReferenceInput;
import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SiteAdminUserService siteAdminUserService;

    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    // For each user subclass we create, each user type will have their own 'create API'. This API will just route the
    // input to the correct user service.
    public User createUser(CreateUserInput input) {
        User newUser;
        switch(input.getSiteRole()) {
            case SITEADMIN:
                newUser = siteAdminUserService.createSiteAdmin(input);
                break;
            // TODO: Create additional case statements once new user services are implemented and added to the repository
            default:
                newUser = null;
        }
        return newUser;
    }

    // For each user subclass we create, each user type will have their own 'update API'. This API will just route the
    // input to the correct user service.
    public User updateUser(UpdateUserInput input) {
        User existingUser;
        switch(input.getSiteRole()) {
            case SITEADMIN:
                existingUser = siteAdminUserService.updateSiteAdmin(input);
                break;
            // TODO: Create additional case statements once new user services are implemented and added to the repository
            default:
                existingUser = null;
        }
        return existingUser;
    }

    public User deleteUser(ReferenceInput requestBody) {
        String existingUserId = requestBody.getId();
        User deletedUser;
        try {
            deletedUser = userRepository.getById(existingUserId);
            userRepository.deleteById(existingUserId);
        } catch (Exception e) {
            LOGGER.info("User with id: " + existingUserId + " not found in repository");
            throw new RuntimeException(e);
        }
        return deletedUser;
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
}
