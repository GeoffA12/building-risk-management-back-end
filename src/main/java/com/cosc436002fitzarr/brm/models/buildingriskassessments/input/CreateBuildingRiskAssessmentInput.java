package com.cosc436002fitzarr.brm.models.buildingriskassessments.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class CreateBuildingRiskAssessmentInput {
    private String publisherId;
    private String buildingId;
    private String title;
    private String description;
    private List<String> riskAssessmentIds;

    @JsonCreator
    public CreateBuildingRiskAssessmentInput(@JsonProperty String publisherId, @JsonProperty String buildingId, @JsonProperty String title,
                                             @JsonProperty String description, @JsonProperty List<String> riskAssessmentIds) {
        this.publisherId = publisherId;
        this.buildingId = buildingId;
        this.title = title;
        this.description = description;
        this.riskAssessmentIds = riskAssessmentIds;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
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

    public List<String> getRiskAssessmentIds() {
        return riskAssessmentIds;
    }

    public void setRiskAssessmentIds(List<String> riskAssessmentIds) {
        this.riskAssessmentIds = riskAssessmentIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateBuildingRiskAssessmentInput that = (CreateBuildingRiskAssessmentInput) o;
        return getPublisherId().equals(that.getPublisherId()) && getBuildingId().equals(that.getBuildingId()) && getTitle().equals(that.getTitle()) && getDescription().equals(that.getDescription()) && getRiskAssessmentIds().equals(that.getRiskAssessmentIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublisherId(), getBuildingId(), getTitle(), getDescription(), getRiskAssessmentIds());
    }

    @Override
    public String toString() {
        return "CreateBuildingRiskAssessmentInput{" +
                "publisherId='" + publisherId + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", riskAssessmentIds='" + riskAssessmentIds + '\'' +
                '}';
    }
}
