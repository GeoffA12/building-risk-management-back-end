package com.cosc436002fitzarr.brm.models.buildingriskassessment;

import com.cosc436002fitzarr.brm.enums.Status;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.riskassessment.Hazard;
import com.cosc436002fitzarr.brm.models.riskassessment.Screener;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "buildingriskassessment")
public class BuildingRiskAssessment implements Entity {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EntityTrail> entityTrail;
    private String publisherId;
    private Long workOrder;
    private String title;
    private String taskDescription;
    private List<BuildingProfile> buildingProfile;
    private List<Hazard> hazards;
    private List<Screener> screeners;
    private List<String> assignedAssociatesIds;
    private String siteId;
    private Status status;

    public BuildingRiskAssessment(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail,
                                  String publisherId, Long workOrder, String title, String taskDescription, List<BuildingProfile> buildingProfile,
                                  List<Hazard> hazards, List<Screener> screeners, List<String> assignedAssociatesIds, String siteId, Status status) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityTrail = entityTrail;
        this.publisherId = publisherId;
        this.workOrder = workOrder;
        this.title = title;
        this.taskDescription = taskDescription;
        this.buildingProfile = buildingProfile;
        this.hazards = hazards;
        this.screeners = screeners;
        this.assignedAssociatesIds = assignedAssociatesIds;
        this.siteId = siteId;
        this.status = status;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public List<EntityTrail> getEntityTrail() {
        return entityTrail;
    }

    @Override
    public void setEntityTrail(List<EntityTrail> entityTrail) {
        this.entityTrail = entityTrail;
    }

    @Override
    public String getPublisherId() {
        return publisherId;
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

    public List<BuildingProfile> getBuildingProfile() {
        return buildingProfile;
    }

    public void setBuildingProfile(List<BuildingProfile> buildingProfile) {
        this.buildingProfile = buildingProfile;
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

    public List<String> getAssignedAssociatesIds() {
        return assignedAssociatesIds;
    }

    public void setAssignedAssociatesIds(List<String> assignedAssociatesIds) {
        this.assignedAssociatesIds = assignedAssociatesIds;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingRiskAssessment that = (BuildingRiskAssessment) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt()) &&
                Objects.equals(getUpdatedAt(), that.getUpdatedAt()) &&
                Objects.equals(getEntityTrail(), that.getEntityTrail()) &&
                Objects.equals(getPublisherId(), that.getPublisherId()) &&
                Objects.equals(getWorkOrder(), that.getWorkOrder()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getTaskDescription(), that.getTaskDescription()) &&
                Objects.equals(getBuildingProfile(), that.getBuildingProfile()) &&
                Objects.equals(getHazards(), that.getHazards()) &&
                Objects.equals(getScreeners(), that.getScreeners()) &&
                Objects.equals(getAssignedAssociatesIds(), that.getAssignedAssociatesIds()) &&
                Objects.equals(getSiteId(), that.getSiteId()) &&
                getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(), getWorkOrder(),
                getTitle(), getTaskDescription(), getBuildingProfile(), getHazards(), getScreeners(), getAssignedAssociatesIds(),
                getSiteId(), getStatus());
    }

    @Override
    public String toString() {
        return "BuildingRiskAssessment{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", entityTrail=" + entityTrail +
                ", publisherId='" + publisherId + '\'' +
                ", workOrder=" + workOrder +
                ", title='" + title + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", buildingProfile=" + buildingProfile +
                ", hazards=" + hazards +
                ", screeners=" + screeners +
                ", assignedAssociatesIds=" + assignedAssociatesIds +
                ", siteId='" + siteId + '\'' +
                ", status=" + status +
                '}';
    }
}
