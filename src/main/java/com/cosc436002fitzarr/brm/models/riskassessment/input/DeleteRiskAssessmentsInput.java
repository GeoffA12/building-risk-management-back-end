package com.cosc436002fitzarr.brm.models.riskassessment.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class DeleteRiskAssessmentsInput {
    private List<String> riskAssessmentIds;
    private String publisherId;

    @JsonCreator
    public DeleteRiskAssessmentsInput(@JsonProperty List<String> riskAssessmentIds, String publisherId) {
        this.riskAssessmentIds = riskAssessmentIds;
        this.publisherId = publisherId;
    }

    public List<String> getRiskAssessmentIds() {
        return riskAssessmentIds;
    }

    public void setRiskAssessmentIds(List<String> riskAssessmentIds) {
        this.riskAssessmentIds = riskAssessmentIds;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteRiskAssessmentsInput that = (DeleteRiskAssessmentsInput) o;
        return getRiskAssessmentIds().equals(that.getRiskAssessmentIds()) && getPublisherId().equals(that.getPublisherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRiskAssessmentIds(), getPublisherId());
    }

    @Override
    public String toString() {
        return "DeleteRiskAssessmentsInput{" +
                "riskAssessmentIds=" + riskAssessmentIds +
                ", publisherId='" + publisherId + '\'' +
                '}';
    }
}
