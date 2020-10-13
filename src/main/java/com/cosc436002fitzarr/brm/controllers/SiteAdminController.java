package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.services.SiteAdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SiteAdminController {

    @Autowired
    private SiteAdminUserService siteAdminUserService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteController.class);

    @PostMapping(value = "/createSiteAdminUser", consumes = "application/json", produces = "application/json")
    public User createSiteAdmin(@RequestBody CreateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteAdminUserService.createSiteAdmin(requestBody);
    }

    @PostMapping(value = "/authenticateUserLogin", consumes = "application/json", produces = "application/json")
    public User authenticateUserLogin(@RequestBody User requestBody) {
        LOGGER.info(requestBody.toString());
        return siteAdminUserService.authenticateUserLogin(requestBody);
    }
}
