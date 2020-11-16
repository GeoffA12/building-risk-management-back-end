package com.cosc436002fitzarr.brm.models.site.input;

import com.cosc436002fitzarr.brm.models.site.Address;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UpdateSiteInput {
    private String userId;
    private String id;
    private String siteName;
    private String siteCode;
    private Address address;

    @JsonCreator
    public UpdateSiteInput(@JsonProperty String userId, @JsonProperty String id,
                           @JsonProperty String siteName, @JsonProperty String siteCode, @JsonProperty Address address) {
        this.userId = userId;
        this.id = id;
        this.siteName = siteName;
        this.siteCode = siteCode;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        UpdateSiteInput that = (UpdateSiteInput) o;
        return getUserId().equals(that.getUserId()) &&
                getSiteName().equals(that.getSiteName()) &&
                getSiteCode().equals(that.getSiteCode()) &&
                getId().equals(that.getId()) &&
                getAddress().equals(that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getId(), getSiteName(), getSiteCode(), getAddress());
    }

    @Override
    public String toString() {
        return "UpdateSiteInput{" +
                "userId='" + userId + '\'' +
                "id='" + id + '\'' +
                ", siteName='" + siteName + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
