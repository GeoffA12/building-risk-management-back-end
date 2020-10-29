package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.user.SiteMaintenanceAssociate;
import com.cosc436002fitzarr.brm.models.user.input.CreateSiteMaintenanceAssociateInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.services.SiteMaintenanceAssociateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SiteMaintenanceAssociateController {
    @Autowired
    public SiteMaintenanceAssociateService siteMaintenanceAssociateService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteMaintenanceAssociateController.class);

    @PostMapping(value = "/createSiteMaintenanceAssociate", produces = "application/json", consumes = "application/json")
    public SiteMaintenanceAssociate createSiteMaintenanceAssociate(@RequestBody CreateSiteMaintenanceAssociateInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteMaintenanceAssociateService.createSiteMaintenanceAssociate(requestBody);
    }

    @GetMapping(value = "/getSiteMaintenanceAssociateById")
    public SiteMaintenanceAssociate getSiteMaintenanceAssociateById(@RequestParam(name = "id") String id) {
        return siteMaintenanceAssociateService.getById(id);
    }

    @PostMapping(value = "/updateSiteMaintenanceAssociate")
    public SiteMaintenanceAssociate updateSiteMaintenanceAssociate(@RequestBody UpdateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteMaintenanceAssociateService.updateSiteMaintenanceAssociate(requestBody);
    }

    @DeleteMapping(value = "/deleteSiteMaintenanceAssociate")
    public String deleteSiteMaintenanceAssociate(@RequestParam(name = "id") String id) {
        SiteMaintenanceAssociate deletedSiteMaintenanceAssociate = siteMaintenanceAssociateService.deleteSiteMaintenanceAssociate(id);
        return "Deleted: " + deletedSiteMaintenanceAssociate.toString() + " from site maintenance associate repository";
    }
}
