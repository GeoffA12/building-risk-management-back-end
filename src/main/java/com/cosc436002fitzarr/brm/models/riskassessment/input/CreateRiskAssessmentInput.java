package com.cosc436002fitzarr.brm.models.riskassessment.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CreateRiskAssessmentInput {
    private String publisherId;
    private String title;
    private String taskDescription;

    @JsonCreator
    public CreateRiskAssessmentInput(@JsonProperty String publisherId, @JsonProperty String title,
                                     @JsonProperty String taskDescription) {
        this.publisherId = publisherId;
        this.title = title;
        this.taskDescription = taskDescription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateRiskAssessmentInput that = (CreateRiskAssessmentInput) o;
        return Objects.equals(getPublisherId(), that.getPublisherId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getTaskDescription(), that.getTaskDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublisherId(), getTitle(), getTaskDescription());
    }

    @Override
    public String toString() {
        return "CreateRiskAssessmentInput{" +
                "publisherId='" + publisherId + '\'' +
                ", title='" + title + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                '}';
    }
}
