package com.cosc436002fitzarr.brm.models.riskassessment;
import com.cosc436002fitzarr.brm.enums.RiskCategory;
import com.cosc436002fitzarr.brm.enums.RiskImpact;

import java.util.Objects;

public class Hazard {
    private String description;
    private RiskImpact riskImpact;
    private String directions;
    private RiskCategory riskCategory;

    public Hazard(String description, RiskImpact riskImpact, String directions, RiskCategory riskCategory) {
        this.description = description;
        this.riskImpact = riskImpact;
        this.directions = directions;
        this.riskCategory = riskCategory;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hazard hazard = (Hazard) o;
        return getDescription().equals(hazard.getDescription()) &&
                getRiskImpact() == hazard.getRiskImpact() &&
                getDirections().equals(hazard.getDirections()) &&
                getRiskCategory() == hazard.getRiskCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getRiskImpact(), getDirections(), getRiskCategory());
    }

    @Override
    public String toString() {
        return "Hazard{" +
                "description='" + description + '\'' +
                ", riskImpact=" + riskImpact +
                ", directions='" + directions + '\'' +
                ", riskCategory=" + riskCategory +
                '}';
    }
}
