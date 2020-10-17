package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SiteAdminUserService siteAdminUserService;

    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateNewUserToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String createHashedPassword(String password) {
        byte[] salt = new byte[16];
        byte[] hash = null;
        for (int i = 0; i < salt.length; ++i) {
            salt[i] = (byte) i;
        }

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException nsae) {
            LOGGER.info(nsae.toString());
        } catch (InvalidKeySpecException ikse) {
            LOGGER.info(ikse.toString());
        }
        return new String(hash);
    }

    // For each user subclass we create, each user type will have their own 'create API'. This API will just route the
    // input to the correct user service.
    public User createUser(CreateUserInput input) {
        User newUser;

        String hashedPassword = createHashedPassword(input.getPassword());
        input.setPassword(hashedPassword);
        String authToken = generateNewUserToken();
        switch(input.getSiteRole()) {
            case SITEADMIN:
                newUser = siteAdminUserService.createSiteAdmin(input, authToken);
                break;
            // TODO: Create additional case statements once new user services are implemented and added to the repository
            default:
                newUser = null;
        }
        return newUser;
    }

    public User authenticateUserLogin(User requestBody) {
        // Default example matcher used here. If we send in a requestBody where { userName: "John", hashPassword: "123Haha" },
        // the example matcher will use an AND operator between the two attributes by default, which is the functionality we want here.
        String hashedPassword = createHashedPassword(requestBody.getHashPassword());
        requestBody.setHashPassword(hashedPassword);

        Example<User> user = Example.of(requestBody);
        // This line will look in the userRepository for the 'user' example matcher defined above. I.E. we're telling Mongo to query all user records
        // where { userName: "John", hashPassword: "123Haha" }
        Optional<User> possibleUser = userRepository.findOne(user);
        User userProfile;
        if (possibleUser.isPresent()) {
            User actualUser = possibleUser.get();
            userProfile = userRepository.findByUsername(actualUser.getUsername());
            LOGGER.info("Fetched: " + actualUser.toString());
        } else {
            userProfile = null;
        }
        return userProfile;
    }
}
