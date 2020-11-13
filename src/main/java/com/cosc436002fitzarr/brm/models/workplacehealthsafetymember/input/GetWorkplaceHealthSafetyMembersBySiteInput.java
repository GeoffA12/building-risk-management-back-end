package com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetWorkplaceHealthSafetyMembersBySiteInput {
    private String userId;
    private List<String> associatedSiteIds;

    @JsonCreator
    public GetWorkplaceHealthSafetyMembersBySiteInput(@JsonProperty String userId, @JsonProperty List<String> associatedSiteIds) {
        this.userId = userId;
        this.associatedSiteIds = associatedSiteIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        GetWorkplaceHealthSafetyMembersBySiteInput that = (GetWorkplaceHealthSafetyMembersBySiteInput) o;
        return getUserId().equals(that.getUserId()) &&
                getAssociatedSiteIds().equals(that.getAssociatedSiteIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getAssociatedSiteIds());
    }

    @Override
    public String toString() {
        return "GetWorkplaceHealthSafetyMembersBySiteInput{" +
                "userId='" + userId + '\'' +
                ", associatedSiteIds=" + associatedSiteIds +
                '}';
    }
}
