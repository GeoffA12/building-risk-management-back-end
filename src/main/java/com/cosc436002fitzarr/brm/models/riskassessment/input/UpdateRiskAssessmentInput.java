package com.cosc436002fitzarr.brm.models.riskassessment.input;

import com.cosc436002fitzarr.brm.models.riskassessment.Hazard;
import com.cosc436002fitzarr.brm.models.riskassessment.Screener;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class UpdateRiskAssessmentInput {
    private String id;
    private String publisherId;
    private String title;
    private String taskDescription;
    private List<Hazard> hazards;
    private List<Screener> screeners;
    private List<String> riskAssessmentScheduleIds;

    @JsonCreator
    public UpdateRiskAssessmentInput(@JsonProperty String id, @JsonProperty String publisherId,
                                     @JsonProperty String title, @JsonProperty String taskDescription,
                                     @JsonProperty List<Hazard> hazards, @JsonProperty List<Screener> screeners,
                                     @JsonProperty List<String> riskAssessmentScheduleIds) {
        this.id = id;
        this.publisherId = publisherId;
        this.title = title;
        this.taskDescription = taskDescription;
        this.hazards = hazards;
        this.screeners = screeners;
        this.riskAssessmentScheduleIds = riskAssessmentScheduleIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
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

    public List<String> getRiskAssessmentScheduleIds() {
        return riskAssessmentScheduleIds;
    }

    public void setRiskAssessmentScheduleIds(List<String> riskAssessmentScheduleIds) {
        this.riskAssessmentScheduleIds = riskAssessmentScheduleIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRiskAssessmentInput that = (UpdateRiskAssessmentInput) o;
        return getId().equals(that.getId()) && getPublisherId().equals(that.getPublisherId()) && getTitle().equals(that.getTitle()) && getTaskDescription().equals(that.getTaskDescription()) && getHazards().equals(that.getHazards()) && getScreeners().equals(that.getScreeners()) && getRiskAssessmentScheduleIds().equals(that.getRiskAssessmentScheduleIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPublisherId(), getTitle(), getTaskDescription(), getHazards(), getScreeners(), getRiskAssessmentScheduleIds());
    }

    @Override
    public String toString() {
        return "UpdateRiskAssessmentInput{" +
                "id='" + id + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", title='" + title + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", hazards=" + hazards +
                ", screeners=" + screeners +
                ", riskAssessmentScheduleIds=" + riskAssessmentScheduleIds +
                '}';
    }
}
