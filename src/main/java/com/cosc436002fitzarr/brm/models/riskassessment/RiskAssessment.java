package com.cosc436002fitzarr.brm.models.riskassessment;

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
    private List<String> siteIds;

    public RiskAssessment(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail,
                          String publisherId, Long workOrder, String title, String taskDescription,
                          List<Hazard> hazards, List<Screener> screeners, List<String> siteIds) {
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
        this.siteIds = siteIds;
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
        RiskAssessment that = (RiskAssessment) o;
        return getId().equals(that.getId()) &&
                getCreatedAt().equals(that.getCreatedAt()) &&
                getUpdatedAt().equals(that.getUpdatedAt()) &&
                getEntityTrail().equals(that.getEntityTrail()) &&
                getPublisherId().equals(that.getPublisherId()) &&
                workOrder.equals(that.workOrder) &&
                title.equals(that.title) &&
                taskDescription.equals(that.taskDescription) &&
                hazards.equals(that.hazards) &&
                screeners.equals(that.screeners) &&
                siteIds.equals(that.siteIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(), workOrder, title, taskDescription, hazards, screeners, siteIds);
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
                ", siteIds=" + siteIds +
                '}';
    }
}
