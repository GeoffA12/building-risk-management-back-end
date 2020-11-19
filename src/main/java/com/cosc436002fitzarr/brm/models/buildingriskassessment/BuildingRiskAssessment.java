package com.cosc436002fitzarr.brm.models.buildingriskassessment;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Objects;

@Document(collection = "buildingriskassessment")
public class BuildingRiskAssessment {
    private List<String> riskAssessmentIds;
    private String buildingId;
    private String title;
    private String description;

    public BuildingRiskAssessment(List<String> riskAssessmentIds, String buildingId, String title, String description) {
        this.riskAssessmentIds = riskAssessmentIds;
        this.buildingId = buildingId;
        this.title = title;
        this.description = description;
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
        return Objects.equals(getRiskAssessmentIds(), that.getRiskAssessmentIds()) &&
                Objects.equals(getBuildingId(), that.getBuildingId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRiskAssessmentIds(), getBuildingId(), getTitle(), getDescription());
    }

    @Override
    public String toString() {
        return "BuildingRiskAssessment{" +
                "riskAssessmentIds=" + riskAssessmentIds +
                ", buildingId='" + buildingId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
