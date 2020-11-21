package com.cosc436002fitzarr.brm.models.buildingriskassessments.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class CreateBuildingRiskAssessmentsInput {
    private String publisherId;
    private List<String> riskAssessmentIds;
    private String buildingId;
    private String title;
    private String description;

    @JsonCreator
    public CreateBuildingRiskAssessmentsInput(@JsonProperty String publisherId, @JsonProperty List<String> riskAssessmentIds,
                                              @JsonProperty String buildingId, @JsonProperty String title, @JsonProperty String description) {
        this.publisherId = publisherId;
        this.riskAssessmentIds = riskAssessmentIds;
        this.buildingId = buildingId;
        this.title = title;
        this.description = description;
    }

    public String getPublisherId() {
        return publisherId;
    }

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
        CreateBuildingRiskAssessmentsInput that = (CreateBuildingRiskAssessmentsInput) o;
        return Objects.equals(getPublisherId(), that.getPublisherId()) &&
                Objects.equals(getRiskAssessmentIds(), that.getRiskAssessmentIds()) &&
                Objects.equals(getBuildingId(), that.getBuildingId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublisherId(), getRiskAssessmentIds(), getBuildingId(), getTitle(), getDescription());
    }

    @Override
    public String toString() {
        return "CreateBuildingRiskAssessmentsInput{" +
                "publisherId='" + publisherId + '\'' +
                ", riskAssessmentIds=" + riskAssessmentIds +
                ", buildingId='" + buildingId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}