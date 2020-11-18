package com.cosc436002fitzarr.brm.models.riskassessment;
import com.cosc436002fitzarr.brm.enums.RiskCategory;
import com.cosc436002fitzarr.brm.enums.RiskImpact;

import java.util.Objects;

public class Hazard {
    private String description;
    private RiskImpact riskImpact;
    private String directions;
    private RiskCategory riskCategory;
    private String comments;
    private Boolean didFulfillHazard;

    public Hazard(String description, RiskImpact riskImpact, String directions, RiskCategory riskCategory, String comments, Boolean didFulfillHazard) {
        this.description = description;
        this.riskImpact = riskImpact;
        this.directions = directions;
        this.riskCategory = riskCategory;
        this.comments = comments;
        this.didFulfillHazard = didFulfillHazard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RiskImpact getRiskImpact() {
        return riskImpact;
    }

    public void setRiskImpact(RiskImpact riskImpact) {
        this.riskImpact = riskImpact;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public RiskCategory getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(RiskCategory riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getDidFulfillHazard() {
        return didFulfillHazard;
    }

    public void setDidFulfillHazard(Boolean didFulfillHazard) {
        this.didFulfillHazard = didFulfillHazard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hazard hazard = (Hazard) o;
        return Objects.equals(getDescription(), hazard.getDescription()) &&
                getRiskImpact() == hazard.getRiskImpact() &&
                Objects.equals(getDirections(), hazard.getDirections()) &&
                getRiskCategory() == hazard.getRiskCategory() &&
                Objects.equals(getComments(), hazard.getComments()) &&
                Objects.equals(getDidFulfillHazard(), hazard.getDidFulfillHazard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getRiskImpact(), getDirections(), getRiskCategory(), getComments(), getDidFulfillHazard());
    }

    @Override
    public String toString() {
        return "Hazard{" +
                "description='" + description + '\'' +
                ", riskImpact=" + riskImpact +
                ", directions='" + directions + '\'' +
                ", riskCategory=" + riskCategory +
                ", comments='" + comments + '\'' +
                ", didFulfillHazard=" + didFulfillHazard +
                '}';
    }
}
