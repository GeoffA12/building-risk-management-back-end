package com.cosc436002fitzarr.brm.models.buildingriskassessments.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class RiskAssessmentAttachmentInput {
    private String riskAssessmentId;
    private List<String> siteMaintenanceAssociateIds;
    private Long workOrder;
    private String dueDate;
    private String buildingId;

    @JsonCreator
    public RiskAssessmentAttachmentInput(@JsonProperty String riskAssessmentId, @JsonProperty List<String> siteMaintenanceAssociateIds,
                                         @JsonProperty Long workOrder, @JsonProperty String dueDate, @JsonProperty String buildingId) {
        this.riskAssessmentId = riskAssessmentId;
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
        this.workOrder = workOrder;
        this.dueDate = dueDate;
        this.buildingId = buildingId;
    }

    public String getRiskAssessmentId() {
        return riskAssessmentId;
    }

    public void setRiskAssessmentId(String riskAssessmentId) {
        this.riskAssessmentId = riskAssessmentId;
    }

    public List<String> getSiteMaintenanceAssociateIds() {
        return siteMaintenanceAssociateIds;
    }

    public void setSiteMaintenanceAssociateIds(List<String> siteMaintenanceAssociateIds) {
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Long getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(Long workOrder) {
        this.workOrder = workOrder;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiskAssessmentAttachmentInput that = (RiskAssessmentAttachmentInput) o;
        return getRiskAssessmentId().equals(that.getRiskAssessmentId()) &&
                getSiteMaintenanceAssociateIds().equals(that.getSiteMaintenanceAssociateIds()) &&
                getWorkOrder().equals(that.getWorkOrder()) &&
                getDueDate().equals(that.getDueDate()) &&
                getBuildingId().equals(that.getBuildingId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRiskAssessmentId(), getSiteMaintenanceAssociateIds(), getWorkOrder(), getDueDate(), getBuildingId());
    }

    @Override
    public String toString() {
        return "RiskAssessmentAttachmentInput{" +
                "riskAssessmentId='" + riskAssessmentId + '\'' +
                ", siteMaintenanceAssociateIds=" + siteMaintenanceAssociateIds +
                ", workOrder=" + workOrder +
                ", dueDate='" + dueDate + '\'' +
                ", buildingId='" + buildingId + '\'' +
                '}';
    }
}
