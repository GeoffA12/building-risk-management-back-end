package com.cosc436002fitzarr.brm.models.site.input;

import com.cosc436002fitzarr.brm.models.ReferenceInput;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetSitesInput {
    private List<String> siteIds;

    @JsonCreator
    public GetSitesInput(@JsonProperty("siteIds") List<String> siteIds) {
        this.siteIds = siteIds;
    }

    public List<String> getSiteIds() {
        return siteIds;
    }

    public void setSiteIds(List<String> siteIds) {
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
