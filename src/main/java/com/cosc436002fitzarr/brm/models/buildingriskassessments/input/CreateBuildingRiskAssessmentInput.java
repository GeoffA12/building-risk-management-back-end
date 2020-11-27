package com.cosc436002fitzarr.brm.models.buildingriskassessments.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class CreateBuildingRiskAssessmentInput {
    private String publisherId;
    private List<String> riskAssessmentIds;
    private List<String> siteMaintenanceAssociateIds;
    private String buildingId;
    private String title;
    private String description;
    private Long workOrder;

    @JsonCreator
    public CreateBuildingRiskAssessmentInput(@JsonProperty String publisherId, @JsonProperty List<String> riskAssessmentIds,
                                             @JsonProperty List<String> siteMaintenanceAssociateIds, @JsonProperty String buildingId,
                                             @JsonProperty String title, @JsonProperty String description, @JsonProperty Long workOrder) {
        this.publisherId = publisherId;
        this.riskAssessmentIds = riskAssessmentIds;
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
        this.buildingId = buildingId;
        this.title = title;
        this.description = description;
        this.workOrder = workOrder;
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
        CreateBuildingRiskAssessmentInput that = (CreateBuildingRiskAssessmentInput) o;
        return getPublisherId().equals(that.getPublisherId()) &&
                getRiskAssessmentIds().equals(that.getRiskAssessmentIds()) &&
                getSiteMaintenanceAssociateIds().equals(that.getSiteMaintenanceAssociateIds()) &&
                getBuildingId().equals(that.getBuildingId()) &&
                getTitle().equals(that.getTitle()) &&
                getDescription().equals(that.getDescription()) &&
                getWorkOrder().equals(that.getWorkOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublisherId(), getRiskAssessmentIds(), getSiteMaintenanceAssociateIds(), getBuildingId(), getTitle(), getDescription(), getWorkOrder());
    }

    @Override
    public String toString() {
        return "CreateBuildingRiskAssessmentsInput{" +
                "publisherId='" + publisherId + '\'' +
                ", riskAssessmentIds=" + riskAssessmentIds +
                ", siteMaintenanceAssociateIds=" + siteMaintenanceAssociateIds +
                ", buildingId='" + buildingId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", workOrder=" + workOrder +
                '}';
    }
}
