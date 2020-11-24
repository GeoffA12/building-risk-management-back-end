package com.cosc436002fitzarr.brm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetEntityBySiteInput {
    private List<String> associatedSiteIds;

    @JsonCreator
    public GetEntityBySiteInput(@JsonProperty List<String> associatedSiteIds) {
        this.associatedSiteIds = associatedSiteIds;
    }

    public List<String> getAssociatedSiteIds() {
        return associatedSiteIds;
    }

    public void setAssociatedSiteIds(List<String> associatedSiteIds) {
        this.associatedSiteIds = associatedSiteIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetEntityBySiteInput that = (GetEntityBySiteInput) o;
        return getAssociatedSiteIds().equals(that.getAssociatedSiteIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssociatedSiteIds());
    }

    @Override
    public String toString() {
        return "GetEntityBySiteInput{" +
                "associatedSiteIds=" + associatedSiteIds +
                '}';
    }
}
