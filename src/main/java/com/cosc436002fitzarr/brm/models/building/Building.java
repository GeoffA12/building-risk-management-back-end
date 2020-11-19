package com.cosc436002fitzarr.brm.models.building;

import com.cosc436002fitzarr.brm.models.site.Address;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Objects;

@Document(collection = "building")
public class Building {
    private String name;
    private String code;
    private String siteId;
    private List<String> buildingRiskAssessmentIds;
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<String> getBuildingRiskAssessmentIds() {
        return buildingRiskAssessmentIds;
    }

    public void setBuildingRiskAssessmentIds(List<String> buildingRiskAssessmentIds) {
        this.buildingRiskAssessmentIds = buildingRiskAssessmentIds;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(getName(), building.getName()) &&
                Objects.equals(getCode(), building.getCode()) &&
                Objects.equals(getSiteId(), building.getSiteId()) &&
                Objects.equals(getBuildingRiskAssessmentIds(), building.getBuildingRiskAssessmentIds()) &&
                Objects.equals(getAddress(), building.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCode(), getSiteId(), getBuildingRiskAssessmentIds(), getAddress());
    }

    @Override
    public String toString() {
        return "Building{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", siteId='" + siteId + '\'' +
                ", buildingRiskAssessmentIds=" + buildingRiskAssessmentIds +
                ", address=" + address +
                '}';
    }
}
