package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.buildingriskassessments.BuildingRiskAssessments;
import com.cosc436002fitzarr.brm.services.BuildingRiskAssessmentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class BuildingRiskAssessmentsController {
    @Autowired
    private BuildingRiskAssessmentsService buildingRiskAssessmentsService;

    private static Logger LOGGER = LoggerFactory.getLogger(BuildingRiskAssessmentsController.class);

//    @PostMapping(value = "/updateBuildingRiskAssessments", consumes = "application/json", produces = "application/json")
//    public BuildingRiskAssessments updateBuildingRiskAssessments(@RequestBody UpdateBuildingRiskAssessmentsInput requestBody) {
//        LOGGER.info(requestBody.toString());
//        return buildingRiskAssessmentsService.updateBuildingRiskAssessments(requestBody);
//    }
//
//    @PostMapping(value = "/createBuildingRiskAssessments", consumes = "application/json", produces = "application/json")
//    public BuildingRiskAssessments createBuildingRiskAssessments(@RequestBody CreateBuildingRiskAssessmentsInput requestBody) {
//        LOGGER.info(requestBody.toString());
//        return buildingRiskAssessmentsService.createBuildingRiskAssessments(requestBody);
//    }
//
//    @GetMapping(value = "/getBuildingRiskAssessmentsById")
//    public BuildingRiskAssessments getBuildingRiskAssessmentsById(@RequestParam(name="id") String id) {
//        return buildingRiskAssessmentsService.getById(id);
//    }
//
//    @DeleteMapping(value = "/deleteBuildingRiskAssessments")
//    public String deleteBuildingRiskAssessments(@RequestParam(name="id") String id, @RequestParam(name="publisherId") String publisherId) {
//        BuildingRiskAssessments deletedBuildingRiskAssessment = buildingRiskAssessmentsService.deleteBuildingRiskAssessments(id, publisherId);
//        return "Deleted: " + deletedBuildingRiskAssessment.toString() + " from building risk assessments repository";
//    }

}
