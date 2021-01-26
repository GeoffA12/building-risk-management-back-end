package com.cosc436002fitzarr.brm.models.riskassessmentschedule.input;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class GetRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsListInput {
    private List<String> riskAssessmentScheduleIds;

    @JsonCreator
    public GetRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsListInput(@JsonProperty List<String> riskAssessmentScheduleIds) {
        this.riskAssessmentScheduleIds = riskAssessmentScheduleIds;
    }

    public List<String> getRiskAssessmentScheduleIds() {
        return riskAssessmentScheduleIds;
    }

    public void setRiskAssessmentScheduleIds(List<String> riskAssessmentScheduleIds) {
        this.riskAssessmentScheduleIds = riskAssessmentScheduleIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsListInput that = (GetRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsListInput) o;
        return Objects.equals(getRiskAssessmentScheduleIds(), that.getRiskAssessmentScheduleIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRiskAssessmentScheduleIds());
    }

    @Override
    public String toString() {
        return "GetRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsListInput{" +
                "riskAssessmentScheduleIds=" + riskAssessmentScheduleIds +
                '}';
    }
}
