package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.services.SiteAdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteAdminController {

    @Autowired
    private SiteAdminUserService siteAdminUserService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteController.class);

}
