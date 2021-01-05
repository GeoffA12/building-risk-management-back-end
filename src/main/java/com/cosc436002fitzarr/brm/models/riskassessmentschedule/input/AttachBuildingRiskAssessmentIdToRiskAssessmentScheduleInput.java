package com.cosc436002fitzarr.brm.models.riskassessmentschedule.input;

import com.cosc436002fitzarr.brm.models.riskassessmentschedule.RiskAssessmentSchedule;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class AttachBuildingRiskAssessmentIdToRiskAssessmentScheduleInput {
    private List<RiskAssessmentSchedule> riskAssessmentScheduleList;
    private String buildingRiskAssessmentId;
    private String publisherId;

    @JsonCreator
    public AttachBuildingRiskAssessmentIdToRiskAssessmentScheduleInput(@JsonProperty List<RiskAssessmentSchedule> riskAssessmentScheduleList, @JsonProperty String buildingRiskAssessmentId,
                                                                       @JsonProperty String publisherId) {
        this.riskAssessmentScheduleList = riskAssessmentScheduleList;
        this.buildingRiskAssessmentId = buildingRiskAssessmentId;
        this.publisherId = publisherId;
    }

    public List<RiskAssessmentSchedule> getRiskAssessmentScheduleList() {
        return riskAssessmentScheduleList;
    }

    public void setRiskAssessmentScheduleList(List<RiskAssessmentSchedule> riskAssessmentScheduleList) {
        this.riskAssessmentScheduleList = riskAssessmentScheduleList;
    }

    public String getBuildingRiskAssessmentId() {
        return buildingRiskAssessmentId;
    }

    public void setBuildingRiskAssessmentId(String buildingRiskAssessmentId) {
        this.buildingRiskAssessmentId = buildingRiskAssessmentId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachBuildingRiskAssessmentIdToRiskAssessmentScheduleInput that = (AttachBuildingRiskAssessmentIdToRiskAssessmentScheduleInput) o;
        return getRiskAssessmentScheduleList().equals(that.getRiskAssessmentScheduleList()) && getBuildingRiskAssessmentId().equals(that.getBuildingRiskAssessmentId()) && getPublisherId().equals(that.getPublisherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRiskAssessmentScheduleList(), getBuildingRiskAssessmentId(), getPublisherId());
    }

    @Override
    public String toString() {
        return "AttachBuildingRiskAssessmentIdToRiskAssessmentScheduleInput{" +
                "riskAssessmentScheduleList=" + riskAssessmentScheduleList +
                ", buildingRiskAssessmentId='" + buildingRiskAssessmentId + '\'' +
                ", publisherId='" + publisherId + '\'' +
                '}';
    }
}
