package com.cosc436002fitzarr.brm.controllers;

import com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.WorkplaceHealthSafetyMember;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.input.GetWorkplaceHealthSafetyMembersBySiteInput;
import com.cosc436002fitzarr.brm.services.WorkplaceHealthSafetyMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkplaceHealthSafetyMemberController {
    @Autowired
    public WorkplaceHealthSafetyMemberService workplaceHealthSafetyMemberService;

    private static Logger LOGGER = LoggerFactory.getLogger(WorkplaceHealthSafetyMember.class);

    @PostMapping(value = "/createWorkplaceHealthSafetyMember", consumes = "application/json", produces = "application/json")
    public WorkplaceHealthSafetyMember createWorkplaceHealthSafetyMember(@RequestBody CreateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return workplaceHealthSafetyMemberService.createWorkPlaceHealthSafetyMember(requestBody);
    }

    @GetMapping(value = "/getWorkplaceHealthSafetyMemberById")
    public WorkplaceHealthSafetyMember getWorkplaceHealthSafetyMemberById(@RequestParam(name = "id") String id) {
        return workplaceHealthSafetyMemberService.getWorkplaceHealthSafetyMemberById(id);
    }

    @PostMapping(value = "/updateWorkplaceHealthSafetyMember")
    public WorkplaceHealthSafetyMember updateWorkplaceHealthSafetyMember(@RequestBody UpdateUserInput requestBody) {
        LOGGER.info(requestBody.toString());
        return workplaceHealthSafetyMemberService.updateWorkplaceHealthSafetyMember(requestBody);
    }

    @DeleteMapping(value = "/deleteWorkplaceHealthSafetyMember")
    public String deleteWorkplaceHealthSafetyMember(@RequestParam(name = "id") String id, @RequestParam(name = "userId") String userId) {
        WorkplaceHealthSafetyMember deletedWorkplaceHealthSafetyMember = workplaceHealthSafetyMemberService.deleteWorkplaceHealthSafetyMember(id, userId);
        return "Workplace health safety member: " + deletedWorkplaceHealthSafetyMember.toString() + " successfully deleted " +
                    "from workplace health and safety and user repositories";
    }

    @PostMapping(value = "/getWorkplaceHealthSafetyMembersBySite")
    public List<WorkplaceHealthSafetyMember> getWorkplaceHealthSafetyMembersBySite(@RequestBody GetWorkplaceHealthSafetyMembersBySiteInput requestBody) {
        LOGGER.info(requestBody.toString());
        return workplaceHealthSafetyMemberService.getWorkplaceHealthSafetyMembersByUserIdAndSite(requestBody.getUserId(), requestBody.getAssociatedSiteIds());
    }
}
