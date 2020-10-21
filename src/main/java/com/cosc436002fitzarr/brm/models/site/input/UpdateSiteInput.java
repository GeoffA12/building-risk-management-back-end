package com.cosc436002fitzarr.brm.models.site.input;

import com.cosc436002fitzarr.brm.models.ReferenceInput;
import com.cosc436002fitzarr.brm.models.site.Address;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UpdateSiteInput {
    private String userId;
    private ReferenceInput referenceInput;
    private String siteName;
    private String siteCode;
    private Address address;

    @JsonCreator
    public UpdateSiteInput(@JsonProperty String userId, @JsonProperty ReferenceInput referenceInput,
                           @JsonProperty String siteName, @JsonProperty String siteCode, @JsonProperty Address address) {
        this.userId = userId;
        this.referenceInput = referenceInput;
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

    public ReferenceInput getReferenceInput() {
        return referenceInput;
    }

    public void setReferenceInput(ReferenceInput referenceInput) {
        this.referenceInput = referenceInput;
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
                getReferenceInput().equals(that.getReferenceInput()) &&
                getAddress().equals(that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getReferenceInput(), getSiteName(), getSiteCode(), getAddress());
    }

    @Override
    public String toString() {
        return "UpdateSiteInput{" +
                "userId='" + userId + '\'' +
                "referenceInput='" + referenceInput + '\'' +
                ", siteName='" + siteName + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
