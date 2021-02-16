package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.GetEntityBySiteInput;
import com.cosc436002fitzarr.brm.models.sitemaintenanceassociate.SiteMaintenanceAssociate;
import com.cosc436002fitzarr.brm.models.sitemaintenanceassociate.input.CreateSiteMaintenanceAssociateInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.services.SiteMaintenanceAssociateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String deleteSiteMaintenanceAssociate(@RequestParam(name = "id") String id, @RequestParam(name="userId") String userId) {
        SiteMaintenanceAssociate deletedSiteMaintenanceAssociate = siteMaintenanceAssociateService.deleteSiteMaintenanceAssociate(id, userId);
        return "Deleted: " + deletedSiteMaintenanceAssociate.toString() + " from site maintenance associate repository";
    }

    @RequestMapping(value = "/getSiteMaintenanceAssociatesBySite", consumes = "application/json", produces = "application/json")
    public List<SiteMaintenanceAssociate> getSiteMaintenanceAssociatesBySite(@RequestBody GetEntityBySiteInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteMaintenanceAssociateService.getSiteMaintenanceAssociatesBySite(requestBody);
    }

    @GetMapping(value = "/getSiteMaintenanceAssociatesBySiteMaintenanceManagerId")
    public List<SiteMaintenanceAssociate> getSiteMaintenanceAssociatesBySiteMaintenanceManagerId(@RequestParam("siteMaintenanceManagerId") String siteMaintenanceManagerId) {
        return siteMaintenanceAssociateService.getSiteMaintenanceAssociatesBySiteMaintenanceManagerId(siteMaintenanceManagerId);
    }
}
