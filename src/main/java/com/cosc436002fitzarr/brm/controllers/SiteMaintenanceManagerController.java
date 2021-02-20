package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.riskassessmentschedule.RiskAssessmentSchedule;
import com.cosc436002fitzarr.brm.models.sitemaintenancemanager.SiteMaintenanceManager;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.services.SiteMaintenanceManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SiteMaintenanceManagerController {
    @Autowired
    public SiteMaintenanceManagerService siteMaintenanceManagerService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteMaintenanceManagerController.class);

    @PostMapping(value = "/createSiteMaintenanceManager", consumes = "application/json", produces = "application/json")
    public SiteMaintenanceManager createSiteMaintenanceManager(@RequestBody CreateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteMaintenanceManagerService.createSiteMaintenanceManager(requestBody);
    }

    @GetMapping(value = "/getSiteMaintenanceManagerById")
    public SiteMaintenanceManager getSiteMaintenanceManagerById(@RequestParam(name = "id") String id) {
        return siteMaintenanceManagerService.getSiteMaintenanceManagerById(id);
    }

    @PostMapping(value = "/updateSiteMaintenanceManager")
    public SiteMaintenanceManager updateSiteMaintenanceManager(@RequestBody UpdateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteMaintenanceManagerService.updateSiteMaintenanceManager(requestBody);
    }

    @DeleteMapping(value = "/deleteSiteMaintenanceManager")
    public String deleteSiteMaintenanceManager(@RequestParam(name = "id") String id, @RequestParam(name = "userId") String userId) {
        SiteMaintenanceManager deletedSiteMaintenanceManager = siteMaintenanceManagerService.deleteSiteMaintenanceManager(id, userId);
        return "Site maintenance manager: " + deletedSiteMaintenanceManager.toString() + " removed from site maintenance manager and user repositories";
    }

    @GetMapping(value = "/getRiskAssessmentSchedulesOfSiteMaintenanceAssociatesOfManager")
    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesOfSiteMaintenanceAssociatesOfManager(@RequestParam(name = "id") String id, @RequestParam(name = "activeSchedules") Boolean activeSchedules) {
        return siteMaintenanceManagerService.getRiskAssessmentSchedulesOfSiteMaintenanceAssociatesOfManager(id);
    }
}
