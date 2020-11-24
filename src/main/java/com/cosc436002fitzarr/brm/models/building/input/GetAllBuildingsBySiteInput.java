package com.cosc436002fitzarr.brm.models.building.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class GetAllBuildingsBySiteInput {
    private String siteId;

    @JsonCreator
    public GetAllBuildingsBySiteInput(@JsonProperty("siteId") String siteId) {
        this.siteId = siteId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetAllBuildingsBySiteInput that = (GetAllBuildingsBySiteInput) o;
        return Objects.equals(getSiteId(), that.getSiteId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSiteId());
    }

    @Override
    public String toString() {
        return "GetAllBuildingsBySiteInput{" +
                "siteId='" + siteId + '\'' +
                '}';
    }
}
