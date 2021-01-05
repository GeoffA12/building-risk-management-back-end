package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.GetEntityBySiteInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessment.BuildingRiskAssessment;
import com.cosc436002fitzarr.brm.models.buildingriskassessment.input.CreateBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.buildingriskassessment.input.UpdateBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.services.BuildingRiskAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BuildingRiskAssessmentController {
    @Autowired
    private BuildingRiskAssessmentService buildingRiskAssessmentsService;

    private static Logger LOGGER = LoggerFactory.getLogger(BuildingRiskAssessmentController.class);

    @PostMapping(value = "/updateBuildingRiskAssessment", consumes = "application/json", produces = "application/json")
    public BuildingRiskAssessment updateBuildingRiskAssessment(@RequestBody UpdateBuildingRiskAssessmentInput requestBody) {
        LOGGER.info(requestBody.toString());
        return buildingRiskAssessmentsService.updateBuildingRiskAssessment(requestBody);
    }

    @PostMapping(value = "/createBuildingRiskAssessment", consumes = "application/json", produces = "application/json")
    public BuildingRiskAssessment createBuildingRiskAssessment(@RequestBody CreateBuildingRiskAssessmentInput requestBody) {
        LOGGER.info(requestBody.toString());
        return buildingRiskAssessmentsService.createBuildingRiskAssessment(requestBody);
    }

    @GetMapping(value = "/getBuildingRiskAssessmentById")
    public BuildingRiskAssessment getBuildingRiskAssessmentById(@RequestParam(name="id") String id) {
        return buildingRiskAssessmentsService.getBuildingRiskAssessmentById(id);
    }

    @DeleteMapping(value = "/deleteBuildingRiskAssessment")
    public String deleteBuildingRiskAssessment(@RequestParam(name="id") String id) {
        BuildingRiskAssessment deletedBuildingRiskAssessment = buildingRiskAssessmentsService.deleteBuildingRiskAssessment(id);
        return "Deleted: " + deletedBuildingRiskAssessment.toString() + " from building risk assessments repository";
    }

    @PostMapping(value = "/getBuildingRiskAssessmentsBySite", consumes = "application/json", produces = "application/json")
    public List<BuildingRiskAssessment> getBuildingRiskAssessmentsBySite(@RequestBody GetEntityBySiteInput requestBody) {
        LOGGER.info(requestBody.toString());
        return buildingRiskAssessmentsService.getBuildingRiskAssessmentsBySite(requestBody);
    }

}
