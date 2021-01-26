package com.cosc436002fitzarr.brm.models.riskassessmentschedule;

import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private List<Hazard> hazards;
    private List<Screener> screeners;
    private RiskLevel riskLevel;
    private LocalDateTime dueDate;

    public RiskAssessmentSchedule(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail,
                                  String publisherId, String title, String riskAssessmentId, String buildingRiskAssessmentId,
                                  List<String> siteMaintenanceAssociateIds, Status status, Long workOrder, List<Hazard> hazards,
                                  List<Screener> screeners, RiskLevel riskLevel, LocalDateTime dueDate) {
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
        this.hazards = hazards;
        this.screeners = screeners;
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

    public List<Hazard> getHazards() { return hazards; }

    public void setHazards(List<Hazard> hazards) { this.hazards = hazards; }

    public List<Screener> getScreeners() { return screeners; }

    public void setScreeners(List<Screener> screeners) { this.screeners = screeners; }

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
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt()) && Objects.equals(getEntityTrail(), that.getEntityTrail()) && Objects.equals(getPublisherId(), that.getPublisherId()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getRiskAssessmentId(), that.getRiskAssessmentId()) && Objects.equals(getBuildingRiskAssessmentId(), that.getBuildingRiskAssessmentId()) && Objects.equals(getSiteMaintenanceAssociateIds(), that.getSiteMaintenanceAssociateIds()) && getStatus() == that.getStatus() && Objects.equals(getWorkOrder(), that.getWorkOrder()) && Objects.equals(getHazards(), that.getHazards()) && Objects.equals(getScreeners(), that.getScreeners()) && getRiskLevel() == that.getRiskLevel() && Objects.equals(getDueDate(), that.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(), getTitle(), getRiskAssessmentId(), getBuildingRiskAssessmentId(), getSiteMaintenanceAssociateIds(), getStatus(), getWorkOrder(), getHazards(), getScreeners(), getRiskLevel(), getDueDate());
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
                ", hazards=" + hazards +
                ", screeners=" + screeners +
                ", riskLevel=" + riskLevel +
                ", dueDate=" + dueDate +
                '}';
    }
}
