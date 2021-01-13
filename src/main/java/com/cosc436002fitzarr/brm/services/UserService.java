package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.PageInput;
import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.AuthenticateUserPasswordInput;
import com.cosc436002fitzarr.brm.utils.PageUtils;


import com.cosc436002fitzarr.brm.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SiteService siteService;

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

    public List<User> getUsersBySiteRole(SiteRole siteRole){
        return userRepository.findBySiteRole(siteRole);
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

    // 2) We already have a built in .findAllById(List<String> ids) method. Use this in other APIs.
    public Map<String, Object> getAllUsersBySite(PageInput input, List<String> siteIds, String userId) {

        Sort sortProperty = Sort.by(input.getSortDirection(), input.getSortBy());
        Pageable page = PageRequest.of(input.getPageNo().intValue(), input.getPageSize().intValue(), sortProperty);

        Page<User> userPages = userRepository.findAll(page);
        List<User> userPageContent = userPages.getContent();
        // TODO: Optimize this code, I think this is running in O(n^2) time complexity. Collections.disjoint will compare the Site Id List of the user
        // and the site ID list of the user stored in the repository. If any of the site ID's are contained in both the input user list and the repository user list,
        // then we have a match, and will return this user in the page
        List<User> filteredUsersBySite = userPageContent.stream()
                .filter(user -> !Collections.disjoint(user.getAssociatedSiteIds(), siteIds) && !user.getId().equals(userId))
                .collect(Collectors.toList());

        Map<String, Object> userResponseMap = PageUtils.getUserMappingResponse(userPages, filteredUsersBySite);
        return userResponseMap;
    }

    public User getUserById(String id) {
        Optional<User> user;
        try {
            user = userRepository.findById(id);
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }

        return user.isPresent() ? user.get() : null;
    }

    public User checkUserExists(String id) {
        Optional<User> potentialUser = userRepository.findById(id);
        if (!potentialUser.isPresent()) {
            LOGGER.info("User with id: " + id + " not found in user repository");
            throw new EntityNotFoundException("User with id: " + id + " not found in user repository");
        }
        return potentialUser.get();
    }

    public Boolean authenticateUserPassword(AuthenticateUserPasswordInput input) {
        User existingUser = checkUserExists(input.getUserId());

        String existingHashedPassword = existingUser.getHashPassword();

        String inputHashedPassword = createHashedPassword(input.getPassword());

        return inputHashedPassword.equals(existingHashedPassword);
    }
}
