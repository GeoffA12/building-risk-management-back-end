package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.riskassessmentschedule.RiskAssessmentSchedule;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.input.AttachBuildingRiskAssessmentIdToRiskAssessmentScheduleInput;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.input.CreateRiskAssessmentScheduleInput;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.input.UpdateRiskAssessmentScheduleInput;
import com.cosc436002fitzarr.brm.services.RiskAssessmentScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RiskAssessmentScheduleController {
    @Autowired
    public RiskAssessmentScheduleService riskAssessmentScheduleService;

    private static Logger LOGGER = LoggerFactory.getLogger(RiskAssessmentScheduleController.class);

    @RequestMapping(value = "/createRiskAssessmentSchedule", consumes = "application/json", produces = "application/json")
    public RiskAssessmentSchedule createRiskAssessmentSchedule(@RequestBody CreateRiskAssessmentScheduleInput requestBody) {
        LOGGER.info(requestBody.toString());
        return riskAssessmentScheduleService.createRiskAssessmentSchedule(requestBody);
    }

    @GetMapping(value = "/getRiskAssessmentScheduleById")
    public RiskAssessmentSchedule getRiskAssessmentScheduleById(@RequestParam(name = "buildingRiskAssessmentId") String buildingRiskAssessmentId) {
        return riskAssessmentScheduleService.getById(buildingRiskAssessmentId);
    }

    @RequestMapping(value = "/updateRiskAssessmentSchedule", consumes = "application/json", produces = "application/json")
    public RiskAssessmentSchedule updateRiskAssessmentSchedule(@RequestBody UpdateRiskAssessmentScheduleInput requestBody) {
        LOGGER.info(requestBody.toString());
        return riskAssessmentScheduleService.updateRiskAssessmentSchedule(requestBody);
    }

    @DeleteMapping(value = "/deleteRiskAssessmentSchedule")
    public String deleteRiskAssessmentSchedule(@RequestParam(name = "buildingRiskAssessmentId") String buildingRiskAssessmentId, @RequestParam(name = "publisherId") String publisherId) {
        RiskAssessmentSchedule deletedRiskAssessmentSchedule = riskAssessmentScheduleService.deleteRiskAssessmentSchedule(buildingRiskAssessmentId, publisherId);
        return "Risk assessment schedule: " + deletedRiskAssessmentSchedule.toString() + " deleted from risk assessment schedule repository";
    }

    @GetMapping(value = "/getRiskAssessmentSchedulesByBuildingRiskAssessmentId")
    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesByBuildingRiskAssessmentId(@RequestParam(name = "buildingRiskAssessmentId") String buildingRiskAssessmentId) {
        return riskAssessmentScheduleService.getRiskAssessmentSchedulesByBuildingRiskAssessmentId(buildingRiskAssessmentId);
    }

    @RequestMapping(value = "/attachBuildingRiskAssessmentIdToRiskAssessmentSchedules", consumes = "application/json", produces = "application/json")
    public String attachBuildingRiskAssessmentIdToRiskAssessmentSchedules(@RequestBody AttachBuildingRiskAssessmentIdToRiskAssessmentScheduleInput requestBody) {
        LOGGER.info(requestBody.toString());
        riskAssessmentScheduleService.attachBuildingRiskAssessmentIdToRiskAssessmentSchedules(requestBody);
        return "Building risk assessment: " + requestBody.getBuildingRiskAssessmentId() + " attached to risk assessment schedules: " + requestBody.getRiskAssessmentScheduleList().toString();
    }
}
