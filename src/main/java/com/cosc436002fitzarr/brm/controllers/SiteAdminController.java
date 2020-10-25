package com.cosc436002fitzarr.brm.controllers;


import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.user.User;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;

import com.cosc436002fitzarr.brm.services.SiteAdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class SiteAdminController {

    @Autowired
    private SiteAdminUserService siteAdminUserService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteController.class);

    @PutMapping(value = "/updateSiteAdminUser", consumes = "application/json", produces = "application/json")
    public User updateSiteAdmin(@RequestBody UpdateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteAdminUserService.updateSiteAdmin(requestBody);
    }

    @GetMapping(value = "/getAllSiteAdmins", produces = "application/json")
    public List<User> getAllSiteAdmins(@RequestParam(name = "siteRole") SiteRole siteRole) {
        return siteAdminUserService.getAllSiteAdmins(siteRole);
    }


}
