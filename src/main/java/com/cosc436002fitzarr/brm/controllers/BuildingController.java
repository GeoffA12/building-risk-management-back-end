package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.building.Building;
import com.cosc436002fitzarr.brm.models.building.input.CreateBuildingInput;
import com.cosc436002fitzarr.brm.models.building.input.UpdateBuildingInput;
import com.cosc436002fitzarr.brm.models.user.input.GetAllUsersBySiteInput;
import com.cosc436002fitzarr.brm.services.BuildingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    private static Logger LOGGER = LoggerFactory.getLogger(BuildingController.class);

    @PostMapping(value = "/createBuilding", consumes = "application/json", produces = "application/json")
    public Building createBuilding(@RequestBody CreateBuildingInput requestBody) {
        LOGGER.info(requestBody.toString());
        return buildingService.createBuilding(requestBody);
    }

    @PutMapping(value = "/updateBuilding", consumes = "application/json", produces = "application/json")
    public Building updateBuilding(@RequestBody UpdateBuildingInput requestBody) {
        LOGGER.info(requestBody.toString());
        return buildingService.updateBuilding(requestBody);
    }

    @GetMapping(value = "/getBuildingById")
    public Building getBuildingById(@RequestParam(name="id") String id) {
        return buildingService.getById(id);
    }

    @PostMapping("/getAllBuildingsBySite")
    public Map<String, Object> getAllBuildingsBySite(@RequestBody GetAllBuildingsBySiteInput input) {
        LOGGER.info(input.toString());
        return buildingService.getAllBuildingsBySite(input.getPageInput(), input.getSiteIds(), input.getBuildingId());
    }

    @DeleteMapping(value = "/deleteBuilding")
    public String deleteBuilding(@RequestParam(name="id") String id) {
        Building deletedBuilding = buildingService.deleteBuilding(id);
        return "Deleted: " + deletedBuilding.toString() + " from building repository";
    }
}
