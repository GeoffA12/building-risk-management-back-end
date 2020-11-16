package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.siteadmin.SiteAdmin;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;

import com.cosc436002fitzarr.brm.services.SiteAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class SiteAdminController {

    @Autowired
    private SiteAdminService siteAdminService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteAdminController.class);

    @PostMapping(value = "/updateSiteAdmin", consumes = "application/json", produces = "application/json")
    public SiteAdmin updateSiteAdmin(@RequestBody UpdateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteAdminService.updateSiteAdmin(requestBody);
    }

    @PostMapping(value = "/createSiteAdmin", consumes = "application/json", produces = "application/json")
    public SiteAdmin createSiteAdmin(@RequestBody CreateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteAdminService.createSiteAdmin(requestBody);
    }

    @GetMapping(value = "/getSiteAdminById")
    public SiteAdmin getSiteAdminById(@RequestParam(name="id") String id) {
        return siteAdminService.getById(id);
    }

    @DeleteMapping(value = "/deleteSiteAdmin")
    public String deleteSiteAdmin(@RequestParam(name="id") String id, @RequestParam(name="userid") String userId) {
        SiteAdmin deletedSiteAdmin = siteAdminService.deleteSiteAdmin(id, userId);
        return "Deleted: " + deletedSiteAdmin.toString() + " from site admin repository";
    }
}
