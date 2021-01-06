package com.cosc436002fitzarr.brm.models.riskassessmentschedule;

import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "riskassessmentschedule")
public class RiskAssessmentSchedule implements Entity {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EntityTrail> entityTrail;
    private String publisherId;
    private String title;
    private String riskAssessmentId;
    private String buildingRiskAssessmentId;
    private List<String> siteMaintenanceAssociateIds;
    private Status status;
    private Long workOrder;
    private RiskLevel riskLevel;
    private LocalDateTime dueDate;

    public RiskAssessmentSchedule(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail,
                                  String publisherId, String title, String riskAssessmentId, String buildingRiskAssessmentId,
                                  List<String> siteMaintenanceAssociateIds, Status status, Long workOrder, RiskLevel riskLevel,
                                  LocalDateTime dueDate) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityTrail = entityTrail;
        this.publisherId = publisherId;
        this.title = title;
        this.riskAssessmentId = riskAssessmentId;
        this.buildingRiskAssessmentId = buildingRiskAssessmentId;
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
        this.status = status;
        this.workOrder = workOrder;
        this.riskLevel = riskLevel;
        this.dueDate = dueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<String> getSiteMaintenanceAssociateIds() {
        return siteMaintenanceAssociateIds;
    }

    public void setSiteMaintenanceAssociateIds(List<String> siteMaintenanceAssociateIds) {
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(Long workOrder) {
        this.workOrder = workOrder;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public List<EntityTrail> getEntityTrail() {
        return entityTrail;
    }

    @Override
    public String getPublisherId() {
        return publisherId;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public void setEntityTrail(List<EntityTrail> entityTrail) {
        this.entityTrail = entityTrail;
    }

    @Override
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiskAssessmentSchedule that = (RiskAssessmentSchedule) o;
        return getId().equals(that.getId()) && getCreatedAt().equals(that.getCreatedAt()) && getUpdatedAt().equals(that.getUpdatedAt()) && getEntityTrail().equals(that.getEntityTrail()) && getPublisherId().equals(that.getPublisherId()) && getTitle().equals(that.getTitle()) && getRiskAssessmentId().equals(that.getRiskAssessmentId()) && getBuildingRiskAssessmentId().equals(that.getBuildingRiskAssessmentId()) && getSiteMaintenanceAssociateIds().equals(that.getSiteMaintenanceAssociateIds()) && getStatus() == that.getStatus() && getWorkOrder().equals(that.getWorkOrder()) && getRiskLevel() == that.getRiskLevel() && getDueDate().equals(that.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(), getTitle(), getRiskAssessmentId(), getBuildingRiskAssessmentId(), getSiteMaintenanceAssociateIds(), getStatus(), getWorkOrder(), getRiskLevel(), getDueDate());
    }

    @Override
    public String toString() {
        return "RiskAssessmentSchedule{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", entityTrail=" + entityTrail +
                ", publisherId='" + publisherId + '\'' +
                ", title='" + title + '\'' +
                ", riskAssessmentId='" + riskAssessmentId + '\'' +
                ", buildingRiskAssessmentId='" + buildingRiskAssessmentId + '\'' +
                ", siteMaintenanceAssociateIds=" + siteMaintenanceAssociateIds +
                ", status=" + status +
                ", workOrder=" + workOrder +
                ", riskLevel=" + riskLevel +
                ", dueDate=" + dueDate +
                '}';
    }
}
