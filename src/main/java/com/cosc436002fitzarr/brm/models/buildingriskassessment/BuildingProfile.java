package com.cosc436002fitzarr.brm.models.buildingriskassessment;

import com.cosc436002fitzarr.brm.enums.RiskLevel;
import java.util.Objects;

public class BuildingProfile {
    private String buildingName;
    private String buildingCode;
    private RiskLevel riskLevel;

    public BuildingProfile(String buildingName, String buildingCode, RiskLevel riskLevel){
        this.buildingName = buildingName;
        this.buildingCode = buildingCode;
        this.riskLevel = riskLevel;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
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
        BuildingProfile that = (BuildingProfile) o;
        return Objects.equals(getBuildingName(), that.getBuildingName()) &&
                Objects.equals(getBuildingCode(), that.getBuildingCode()) &&
                getRiskLevel() == that.getRiskLevel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuildingName(), getBuildingCode(), getRiskLevel());
    }

    @Override
    public String toString() {
        return "BuildingProfile{" +
                "buildingName='" + buildingName + '\'' +
                ", buildingCode='" + buildingCode + '\'' +
                ", riskLevel=" + riskLevel +
                '}';
    }
}
