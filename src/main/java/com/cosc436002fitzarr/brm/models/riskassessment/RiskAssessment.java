package com.cosc436002fitzarr.brm.models.riskassessment;

import com.cosc436002fitzarr.brm.enums.RiskImpact;
import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "riskassessment")
public class RiskAssessment implements Entity {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EntityTrail> entityTrail;
    private String publisherId;
    private Long workOrder;
    private String title;
    private String taskDescription;
    private List<Hazard> hazards;
    private List<Screener> screeners;
    private String buildingId;
    private Status status;
    private LocalDateTime dueDate;
    private RiskLevel riskLevel;
    private List<String> siteMaintenanceAssociateIds;

    public RiskAssessment(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail,
                          String publisherId, Long workOrder, String title, String taskDescription, List<Hazard> hazards,
                          List<Screener> screeners, String buildingId, Status status, LocalDateTime dueDate, RiskLevel riskLevel,
                          List<String> siteMaintenanceAssociateIds) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityTrail = entityTrail;
        this.publisherId = publisherId;
        this.workOrder = workOrder;
        this.title = title;
        this.taskDescription = taskDescription;
        this.hazards = hazards;
        this.screeners = screeners;
        this.buildingId = buildingId;
        this.status = status;
        this.dueDate = dueDate;
        this.riskLevel = riskLevel;
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
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

    public Long getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(Long workOrder) {
        this.workOrder = workOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public List<Hazard> getHazards() {
        return hazards;
    }

    public void setHazards(List<Hazard> hazards) {
        this.hazards = hazards;
    }

    public List<Screener> getScreeners() {
        return screeners;
    }

    public void setScreeners(List<Screener> screeners) {
        this.screeners = screeners;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiskAssessment that = (RiskAssessment) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt()) &&
                Objects.equals(getUpdatedAt(), that.getUpdatedAt()) &&
                Objects.equals(getEntityTrail(), that.getEntityTrail()) &&
                Objects.equals(getPublisherId(), that.getPublisherId()) &&
                Objects.equals(getWorkOrder(), that.getWorkOrder()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getTaskDescription(), that.getTaskDescription()) &&
                Objects.equals(getHazards(), that.getHazards()) &&
                Objects.equals(getScreeners(), that.getScreeners()) &&
                Objects.equals(getBuildingId(), that.getBuildingId()) &&
                getStatus() == that.getStatus() &&
                Objects.equals(getDueDate(), that.getDueDate()) &&
                getRiskLevel() == that.getRiskLevel() &&
                Objects.equals(getSiteMaintenanceAssociateIds(), that.getSiteMaintenanceAssociateIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(), getWorkOrder(), getTitle(), getTaskDescription(), getHazards(), getScreeners(), getBuildingId(), getStatus(), getDueDate(), getRiskLevel(), getSiteMaintenanceAssociateIds());
    }

    @Override
    public String toString() {
        return "RiskAssessment{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", entityTrail=" + entityTrail +
                ", publisherId='" + publisherId + '\'' +
                ", workOrder=" + workOrder +
                ", title='" + title + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", hazards=" + hazards +
                ", screeners=" + screeners +
                ", buildingId='" + buildingId + '\'' +
                ", status=" + status +
                ", dueDate=" + dueDate +
                ", riskLevel=" + riskLevel +
                ", siteMaintenanceAssociateIds=" + siteMaintenanceAssociateIds +
                '}';
    }
}
