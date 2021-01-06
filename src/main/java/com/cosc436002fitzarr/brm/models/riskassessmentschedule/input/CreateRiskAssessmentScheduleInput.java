package com.cosc436002fitzarr.brm.models.riskassessmentschedule.input;

import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class CreateRiskAssessmentScheduleInput {
    private String title;
    private String publisherId;
    private Status status;
    private String dueDate;
    private RiskLevel riskLevel;
    private List<String> siteMaintenanceAssociateIds;
    private Long workOrder;
    private String buildingRiskAssessmentId;
    private String riskAssessmentId;

    @JsonCreator
    public CreateRiskAssessmentScheduleInput(@JsonProperty String title, @JsonProperty String publisherId, @JsonProperty Status status, @JsonProperty String dueDate,
                                             @JsonProperty RiskLevel riskLevel, @JsonProperty List<String> siteMaintenanceAssociateIds,
                                             @JsonProperty Long workOrder, @JsonProperty String buildingRiskAssessmentId, @JsonProperty String riskAssessmentId) {
        this.title = title;
        this.publisherId = publisherId;
        this.status = status;
        this.dueDate = dueDate;
        this.riskLevel = riskLevel;
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
        this.workOrder = workOrder;
        this.buildingRiskAssessmentId = buildingRiskAssessmentId;
        this.riskAssessmentId = riskAssessmentId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getRiskAssessmentId() {
        return riskAssessmentId;
    }

    public void setRiskAssessmentId(String riskAssessmentId) {
        this.riskAssessmentId = riskAssessmentId;
    }

    public String getBuildingRiskAssessmentId() {
        return buildingRiskAssessmentId;
    }

    public void setBuildingRiskAssessmentId(String buildingRiskAssessmentId) {
        this.buildingRiskAssessmentId = buildingRiskAssessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public List<String> getSiteMaintenanceAssociateIds() {
        return siteMaintenanceAssociateIds;
    }

    public void setSiteMaintenanceAssociateIds(List<String> siteMaintenanceAssociateIds) {
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
    }

    public Long getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(Long workOrder) {
        this.workOrder = workOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateRiskAssessmentScheduleInput that = (CreateRiskAssessmentScheduleInput) o;
        return getTitle().equals(that.getTitle()) && getPublisherId().equals(that.getPublisherId()) && getStatus() == that.getStatus() && getDueDate().equals(that.getDueDate()) && getRiskLevel() == that.getRiskLevel() && getSiteMaintenanceAssociateIds().equals(that.getSiteMaintenanceAssociateIds()) && getWorkOrder().equals(that.getWorkOrder()) && getBuildingRiskAssessmentId().equals(that.getBuildingRiskAssessmentId()) && getRiskAssessmentId().equals(that.getRiskAssessmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPublisherId(), getStatus(), getDueDate(), getRiskLevel(), getSiteMaintenanceAssociateIds(), getWorkOrder(), getBuildingRiskAssessmentId(), getRiskAssessmentId());
    }

    @Override
    public String toString() {
        return "CreateRiskAssessmentScheduleInput{" +
                "title='" + title + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", status=" + status +
                ", dueDate='" + dueDate + '\'' +
                ", riskLevel=" + riskLevel +
                ", siteMaintenanceAssociateIds=" + siteMaintenanceAssociateIds +
                ", workOrder=" + workOrder +
                ", buildingRiskAssessmentId='" + buildingRiskAssessmentId + '\'' +
                ", riskAssessmentId='" + riskAssessmentId + '\'' +
                '}';
    }
}
