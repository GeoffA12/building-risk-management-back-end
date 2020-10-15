package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    public UserService userService;

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/authenticateUserLogin", consumes = "application/json", produces = "application/json")
    public User authenticateUserLogin(@RequestBody User requestBody) {
        LOGGER.info(requestBody.toString());
        return userService.authenticateUserLogin(requestBody);
    }

    @PostMapping(value = "/createUser", consumes = "application/json", produces = "application/json")
    public User createSiteAdmin(@RequestBody CreateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return userService.createUser(requestBody);
    }
}
