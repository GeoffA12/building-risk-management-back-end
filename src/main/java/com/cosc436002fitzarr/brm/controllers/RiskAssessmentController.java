package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.buildingriskassessments.input.AttachRiskAssessmentsToBuildingRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import com.cosc436002fitzarr.brm.models.riskassessment.input.CreateRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.riskassessment.input.GetAllRiskAssessmentsBySiteInput;
import com.cosc436002fitzarr.brm.models.riskassessment.input.UpdateRiskAssessmentInput;
import com.cosc436002fitzarr.brm.services.RiskAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RiskAssessmentController {

    @Autowired
    private RiskAssessmentService riskAssessmentService;

    private static Logger LOGGER = LoggerFactory.getLogger(RiskAssessmentController.class);

    @PostMapping(value = "/updateRiskAssessment", consumes = "application/json", produces = "application/json")
    public RiskAssessment updateRiskAssessment(@RequestBody UpdateRiskAssessmentInput requestBody) {
        LOGGER.info(requestBody.toString());
        return riskAssessmentService.updateRiskAssessment(requestBody);
    }

    @PostMapping(value = "/createRiskAssessment", consumes = "application/json", produces = "application/json")
    public RiskAssessment createRiskAssessment(@RequestBody CreateRiskAssessmentInput requestBody) {
        LOGGER.info(requestBody.toString());
        return riskAssessmentService.createRiskAssessment(requestBody);
    }

    @GetMapping(value = "/getRiskAssessmentById")
    public RiskAssessment getRiskAssessmentById(@RequestParam(name="id") String id) {
        return riskAssessmentService.getById(id);
    }

    @DeleteMapping(value = "/deleteRiskAssessment")
    public String deleteRiskAssessment(@RequestParam(name="id") String id, @RequestParam(name="publisherId") String publisherId) {
        RiskAssessment deletedRiskAssessment = riskAssessmentService.deleteRiskAssessment(id, publisherId);
        return "Deleted: " + deletedRiskAssessment.toString() + " from risk assessment repository";
    }

    @PostMapping(value = "/getRiskAssessmentsBySite")
    public Map<String, Object> getRiskAssessmentsBySite(@RequestBody GetAllRiskAssessmentsBySiteInput requestBody) {
        LOGGER.info(requestBody.toString());
        return riskAssessmentService.getRiskAssessmentsBySite(requestBody);
    }

    @PostMapping(value = "/attachRiskAssessmentSchedulesToRiskAssessment", consumes = "application/json")
    public String attachRiskAssessmentSchedulesToRiskAssessment(@RequestBody AttachRiskAssessmentsToBuildingRiskAssessmentInput requestBody) {
        LOGGER.info(requestBody.toString());
        riskAssessmentService.attachRiskAssessmentSchedulesToRiskAssessment(requestBody);
        return "Risk assessment schedules successfully added.";
    }

    @PostMapping(value = "/removeRiskAssessmentSchedulesFromRiskAssessment", consumes = "application/json")
    public String removeRiskAssessmentSchedulesFromRiskAssessment(@RequestBody AttachRiskAssessmentsToBuildingRiskAssessmentInput requestBody) {
        LOGGER.info(requestBody.toString());
        riskAssessmentService.removeRiskAssessmentSchedulesFromRiskAssessment(requestBody);
        return "Risk assessment schedules successfully removed";
    }
}
