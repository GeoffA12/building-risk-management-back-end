package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.user.User;

import com.cosc436002fitzarr.brm.models.user.input.AuthenticateUserPasswordInput;
import com.cosc436002fitzarr.brm.models.user.input.GetAllUsersBySiteInput;

import com.cosc436002fitzarr.brm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    public UserService userService;

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/authenticateUserLogin", consumes = "application/json", produces = "application/json")
    public User authenticateUserLogin(@RequestBody User requestBody) {
        return userService.authenticateUserLogin(requestBody);
    }

    @PostMapping("/getAllUsersBySite")
    public Map<String, Object> getAllUsersBySite(@RequestBody GetAllUsersBySiteInput input) {
        LOGGER.info(input.toString());
        return userService.getAllUsersBySite(input.getPageInput(), input.getSiteIds(), input.getUserId());
    }

    @GetMapping(value = "/getUsersBySiteRole", produces = "application/json")
    public List<User> getUsersBySiteRole(@RequestParam(name = "siteRole") SiteRole siteRole) {
        return userService.getUsersBySiteRole(siteRole);
    }

    @GetMapping(value = "/getUserById")
    public User getUserById(@RequestParam(name = "id") String id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/authenticateUserPassword", consumes = "application/json", produces = "application/json")
    public Boolean authenticateUserPassword(@RequestBody AuthenticateUserPasswordInput requestBody) {
        LOGGER.info(requestBody.toString());
        return userService.authenticateUserPassword(requestBody);
    }
}
