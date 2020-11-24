package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.site.input.*;
import com.cosc436002fitzarr.brm.models.site.Site;
import com.cosc436002fitzarr.brm.services.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SiteController {

    @Autowired
    private SiteService siteService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteController.class);

    @PostMapping(value = "/createSite", consumes = "application/json", produces = "application/json")
    public Site createSite(@RequestBody CreateSiteInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteService.createSite(requestBody);
    }

    // If we wanted the id as a URL parameter, the URL could be "/{id}". However, the id would need to be a @RequestParam as well. This is usually done when we're only
    // dealing with single objects
    @RequestMapping(value = "/getSiteById")
    public Site getById(@RequestParam(name = "id") String id) {
        return siteService.getById(id);
    }

    @PostMapping(value = "/getSitesByName", consumes = "application/json", produces = "application/json")
    public List<Site> findBySiteName(@RequestBody GetSitesByNameInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteService.findBySiteName(requestBody);
    }

    @PostMapping(value = "/getSitesByCode", consumes = "application/json", produces = "application/json")
    public List<Site> findBySiteCode(@RequestBody GetSitesByCodeInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteService.findBySiteCode(requestBody);
    }

    @PutMapping(value = "/updateSite", consumes = "application/json", produces = "application/json")
    public Site updateSite(@RequestBody UpdateSiteInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteService.updateSite(requestBody);
    }

    @RequestMapping("/getAllSites")
    public List<Site> getAllSites() {
        return siteService.getAllSites();
    }

    @PostMapping(value = "/getSites")
    public List<Site> getSites(@RequestBody GetSitesInput requestBody) {
        LOGGER.info(requestBody.toString());
        return siteService.getSites(requestBody);
    }

    @DeleteMapping(value = "/deleteSite")
    public String deleteSite(@RequestParam(name = "id") String id) {
        siteService.deleteSite(id);
        return "Site with id: " + id + "deleted from the repository.";
    }


}
