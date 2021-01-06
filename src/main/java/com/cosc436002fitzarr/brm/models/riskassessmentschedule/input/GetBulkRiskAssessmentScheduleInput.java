package com.cosc436002fitzarr.brm.models.riskassessmentschedule.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetBulkRiskAssessmentScheduleInput {
    private List<String> riskAssessmentIds;

    @JsonCreator
    public GetBulkRiskAssessmentScheduleInput(@JsonProperty List<String> riskAssessmentIds) {
        this.riskAssessmentIds = riskAssessmentIds;
    }

    public List<String> getRiskAssessmentIds() {
        return riskAssessmentIds;
    }

    public void setRiskAssessmentIds(List<String> riskAssessmentIds) {
        this.riskAssessmentIds = riskAssessmentIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetBulkRiskAssessmentScheduleInput that = (GetBulkRiskAssessmentScheduleInput) o;
        return getRiskAssessmentIds().equals(that.getRiskAssessmentIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRiskAssessmentIds());
    }

    @Override
    public String toString() {
        return "GetBulkRiskAssessmentScheduleInput{" +
                "riskAssessmentIds=" + riskAssessmentIds +
                '}';
    }
}
