package com.cosc436002fitzarr.brm.models.site.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetSitesInput {
    private List<ReferenceInput> siteIds;

    @JsonCreator
    public GetSitesInput(@JsonProperty("siteIds") List<ReferenceInput> siteIds) {
        this.siteIds = siteIds;
    }

    public List<ReferenceInput> getSiteIds() {
        return siteIds;
    }

    public void setSiteIds(List<ReferenceInput> siteIds) {
        this.siteIds = siteIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetSitesInput that = (GetSitesInput) o;
        return getSiteIds().equals(that.getSiteIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSiteIds());
    }

    @Override
    public String toString() {
        return "GetSitesInput{" +
                "siteIds=" + siteIds +
                '}';
    }
}
