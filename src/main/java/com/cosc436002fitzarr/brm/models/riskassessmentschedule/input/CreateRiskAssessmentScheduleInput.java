package com.cosc436002fitzarr.brm.models.riskassessmentschedule.input;

import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.Hazard;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.Screener;
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
    private List<Hazard> hazards;
    private List<Screener> screeners;
    private String buildingRiskAssessmentId;
    private String riskAssessmentId;

    @JsonCreator
    public CreateRiskAssessmentScheduleInput(@JsonProperty String title, @JsonProperty String publisherId, @JsonProperty Status status, @JsonProperty String dueDate,
                                             @JsonProperty RiskLevel riskLevel, @JsonProperty List<String> siteMaintenanceAssociateIds, @JsonProperty Long workOrder,
                                             @JsonProperty List<Hazard> hazards, @JsonProperty List<Screener> screeners, @JsonProperty String buildingRiskAssessmentId,
                                             @JsonProperty String riskAssessmentId) {
        this.title = title;
        this.publisherId = publisherId;
        this.status = status;
        this.dueDate = dueDate;
        this.riskLevel = riskLevel;
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
        this.workOrder = workOrder;
        this.hazards = hazards;
        this.screeners = screeners;
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

    public List<Hazard> getHazards() { return hazards; }

    public void setHazards(List<Hazard> hazards) { this.hazards = hazards; }

    public List<Screener> getScreeners() { return screeners; }

    public void setScreeners(List<Screener> screeners) { this.screeners = screeners; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateRiskAssessmentScheduleInput that = (CreateRiskAssessmentScheduleInput) o;
        return Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getPublisherId(), that.getPublisherId()) && getStatus() == that.getStatus() && Objects.equals(getDueDate(), that.getDueDate()) && getRiskLevel() == that.getRiskLevel() && Objects.equals(getSiteMaintenanceAssociateIds(), that.getSiteMaintenanceAssociateIds()) && Objects.equals(getWorkOrder(), that.getWorkOrder()) && Objects.equals(getHazards(), that.getHazards()) && Objects.equals(getScreeners(), that.getScreeners()) && Objects.equals(getBuildingRiskAssessmentId(), that.getBuildingRiskAssessmentId()) && Objects.equals(getRiskAssessmentId(), that.getRiskAssessmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPublisherId(), getStatus(), getDueDate(), getRiskLevel(), getSiteMaintenanceAssociateIds(), getWorkOrder(), getHazards(), getScreeners(), getBuildingRiskAssessmentId(), getRiskAssessmentId());
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
                ", hazards=" + hazards +
                ", screeners=" + screeners +
                ", buildingRiskAssessmentId='" + buildingRiskAssessmentId + '\'' +
                ", riskAssessmentId='" + riskAssessmentId + '\'' +
                '}';
    }
}
