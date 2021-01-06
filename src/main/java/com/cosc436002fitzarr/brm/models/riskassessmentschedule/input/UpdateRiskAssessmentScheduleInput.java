package com.cosc436002fitzarr.brm.models.riskassessmentschedule.input;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UpdateRiskAssessmentScheduleInput {
    private CreateRiskAssessmentScheduleInput createRiskAssessmentScheduleInput;
    private String id;

    @JsonCreator
    public UpdateRiskAssessmentScheduleInput(@JsonProperty CreateRiskAssessmentScheduleInput createRiskAssessmentScheduleInput, @JsonProperty String id) {
        this.createRiskAssessmentScheduleInput = createRiskAssessmentScheduleInput;
        this.id = id;
    }

    public CreateRiskAssessmentScheduleInput getCreateRiskAssessmentScheduleInput() {
        return createRiskAssessmentScheduleInput;
    }

    public void setCreateRiskAssessmentScheduleInput(CreateRiskAssessmentScheduleInput createRiskAssessmentScheduleInput) {
        this.createRiskAssessmentScheduleInput = createRiskAssessmentScheduleInput;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRiskAssessmentScheduleInput that = (UpdateRiskAssessmentScheduleInput) o;
        return getCreateRiskAssessmentScheduleInput().equals(that.getCreateRiskAssessmentScheduleInput()) && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreateRiskAssessmentScheduleInput(), getId());
    }

    @Override
    public String toString() {
        return "UpdateRiskAssessmentScheduleInput{" +
                "createRiskAssessmentScheduleInput=" + createRiskAssessmentScheduleInput +
                ", id='" + id + '\'' +
                '}';
    }
}
