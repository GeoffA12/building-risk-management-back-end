package com.cosc436002fitzarr.brm.models.riskassessment;

import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class RiskAssessmentSchedule {
    private String buildingId;
    private Status status;
    private LocalDateTime dueDate;
    private RiskLevel riskLevel;
    private List<String> siteMaintenanceAssociateIds;
    private Long workOrder;

    public RiskAssessmentSchedule(String buildingId, Status status, LocalDateTime dueDate, RiskLevel riskLevel, List<String> siteMaintenanceAssociateIds,
                                  Long workOrder) {
        this.buildingId = buildingId;
        this.status = status;
        this.dueDate = dueDate;
        this.riskLevel = riskLevel;
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
        this.workOrder = workOrder;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
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
        RiskAssessmentSchedule that = (RiskAssessmentSchedule) o;
        return getBuildingId().equals(that.getBuildingId()) &&
                getStatus() == that.getStatus() &&
                getDueDate().equals(that.getDueDate()) && getRiskLevel() == that.getRiskLevel() &&
                getSiteMaintenanceAssociateIds().equals(that.getSiteMaintenanceAssociateIds()) &&
                getWorkOrder().equals(that.getWorkOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuildingId(), getStatus(), getDueDate(), getRiskLevel(), getSiteMaintenanceAssociateIds(), getWorkOrder());
    }

    @Override
    public String toString() {
        return "RiskAssessmentSchedule{" +
                "buildingId='" + buildingId + '\'' +
                ", status=" + status +
                ", dueDate=" + dueDate +
                ", riskLevel=" + riskLevel +
                ", siteMaintenanceAssociateIds=" + siteMaintenanceAssociateIds +
                ", workOrder=" + workOrder +
                '}';
    }
}
