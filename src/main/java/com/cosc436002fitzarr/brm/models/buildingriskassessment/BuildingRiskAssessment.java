package com.cosc436002fitzarr.brm.models.buildingriskassessment;

import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
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
    private List<String> riskAssessmentIds;
    private String buildingId;
    private String title;
    private String description;

    public BuildingRiskAssessment(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail,
                                  String publisherId, List<String> riskAssessmentIds, String buildingId, String title, String description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityTrail = entityTrail;
        this.publisherId = publisherId;
        this.riskAssessmentIds = riskAssessmentIds;
        this.buildingId = buildingId;
        this.title = title;
        this.description = description;
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

    public List<String> getRiskAssessmentIds() {
        return riskAssessmentIds;
    }

    public void setRiskAssessmentIds(List<String> riskAssessmentIds) {
        this.riskAssessmentIds = riskAssessmentIds;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                Objects.equals(getRiskAssessmentIds(), that.getRiskAssessmentIds()) &&
                Objects.equals(getBuildingId(), that.getBuildingId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(),
                getRiskAssessmentIds(), getBuildingId(), getTitle(), getDescription());
    }

    @Override
    public String toString() {
        return "BuildingRiskAssessment{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", entityTrail=" + entityTrail +
                ", publisherId='" + publisherId + '\'' +
                ", riskAssessmentIds=" + riskAssessmentIds +
                ", buildingId='" + buildingId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
