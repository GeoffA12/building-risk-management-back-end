package com.cosc436002fitzarr.brm.models.riskassessment.input;

import com.cosc436002fitzarr.brm.models.riskassessment.Hazard;
import com.cosc436002fitzarr.brm.models.riskassessment.Screener;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class CreateRiskAssessmentInput {
    private String publisherId;
    private Long workOrder;
    private String title;
    private String taskDescription;
    private List<Hazard> hazards;
    private List<Screener> screeners;

    @JsonCreator
    public CreateRiskAssessmentInput(@JsonProperty String publisherId, @JsonProperty Long workOrder, @JsonProperty String title,
                                     @JsonProperty String taskDescription, @JsonProperty List<Hazard> hazards,
                                     @JsonProperty List<Screener> screeners) {
        this.publisherId = publisherId;
        this.workOrder = workOrder;
        this.title = title;
        this.taskDescription = taskDescription;
        this.hazards = hazards;
        this.screeners = screeners;
    }

    public String getPublisherId() {
        return publisherId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateRiskAssessmentInput that = (CreateRiskAssessmentInput) o;
        return Objects.equals(getPublisherId(), that.getPublisherId()) &&
                Objects.equals(getWorkOrder(), that.getWorkOrder()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getTaskDescription(), that.getTaskDescription()) &&
                Objects.equals(getHazards(), that.getHazards()) &&
                Objects.equals(getScreeners(), that.getScreeners());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublisherId(), getWorkOrder(), getTitle(), getTaskDescription(), getHazards(), getScreeners());
    }

    @Override
    public String toString() {
        return "CreateRiskAssessmentInput{" +
                "publisherId='" + publisherId + '\'' +
                ", workOrder=" + workOrder +
                ", title='" + title + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", hazards=" + hazards +
                ", screeners=" + screeners +
                '}';
    }
}
