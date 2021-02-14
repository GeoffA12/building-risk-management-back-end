package com.cosc436002fitzarr.brm.models.riskassessmentschedule.input;

import com.cosc436002fitzarr.brm.enums.RiskLevel;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.Hazard;
import com.cosc436002fitzarr.brm.models.riskassessmentschedule.Screener;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class SubmitRiskAssessmentScheduleInput {
    private String id;
    private String publisherId;
    private List<Screener> screeners;
    private List<Hazard> hazards;
    private RiskLevel riskLevel;

    @JsonCreator
    public SubmitRiskAssessmentScheduleInput(@JsonProperty String id, @JsonProperty String publisherId,
                                             @JsonProperty List<Screener> screeners, @JsonProperty List<Hazard> hazards,
                                             @JsonProperty RiskLevel riskLevel) {
        this.id = id;
        this.publisherId = publisherId;
        this.screeners = screeners;
        this.hazards = hazards;
        this.riskLevel = riskLevel;
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

    public List<Screener> getScreeners() {
        return screeners;
    }

    public void setScreeners(List<Screener> screeners) {
        this.screeners = screeners;
    }

    public List<Hazard> getHazards() {
        return hazards;
    }

    public void setHazards(List<Hazard> hazards) {
        this.hazards = hazards;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmitRiskAssessmentScheduleInput that = (SubmitRiskAssessmentScheduleInput) o;
        return getId().equals(that.getId()) && getPublisherId().equals(that.getPublisherId()) && getScreeners().equals(that.getScreeners()) && getHazards().equals(that.getHazards()) && getRiskLevel() == that.getRiskLevel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPublisherId(), getScreeners(), getHazards(), getRiskLevel());
    }

    @Override
    public String toString() {
        return "SubmitRiskAssessmentScheduleInput{" +
                "id='" + id + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", screeners=" + screeners +
                ", hazards=" + hazards +
                ", riskLevel=" + riskLevel +
                '}';
    }
}
