package com.cosc436002fitzarr.brm.models.buildingriskassessments.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class AttachRiskAssessmentsToBuildingRiskAssessmentInput {
    private List<RiskAssessmentAttachmentInput> riskAssessmentAttachmentInputList;
    private String publisherId;

    @JsonCreator
    public AttachRiskAssessmentsToBuildingRiskAssessmentInput(@JsonProperty List<RiskAssessmentAttachmentInput> riskAssessmentAttachmentInputList,
                                                              @JsonProperty String publisherId) {
        this.riskAssessmentAttachmentInputList = riskAssessmentAttachmentInputList;
        this.publisherId = publisherId;
    }

    public List<RiskAssessmentAttachmentInput> getRiskAssessmentAttachmentInputList() {
        return riskAssessmentAttachmentInputList;
    }

    public void setRiskAssessmentAttachmentInputList(List<RiskAssessmentAttachmentInput> riskAssessmentAttachmentInputList) {
        this.riskAssessmentAttachmentInputList = riskAssessmentAttachmentInputList;
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
        AttachRiskAssessmentsToBuildingRiskAssessmentInput that = (AttachRiskAssessmentsToBuildingRiskAssessmentInput) o;
        return getRiskAssessmentAttachmentInputList().equals(that.getRiskAssessmentAttachmentInputList()) &&
                getPublisherId().equals(that.getPublisherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRiskAssessmentAttachmentInputList(), getPublisherId());
    }

    @Override
    public String toString() {
        return "AttachRiskAssessmentsToBuildingRiskAssessmentInput{" +
                "riskAssessmentAttachmentInputList=" + riskAssessmentAttachmentInputList +
                "publisherId=" + publisherId +
                '}';
    }
}
