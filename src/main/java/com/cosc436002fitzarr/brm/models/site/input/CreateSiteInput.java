package com.cosc436002fitzarr.brm.models.site.input;

import com.cosc436002fitzarr.brm.models.site.Address;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CreateSiteInput {
    String publisherId;
    String siteName;
    String siteCode;
    Address address;

    @JsonCreator
    public CreateSiteInput(@JsonProperty("publisherId") String publisherId, @JsonProperty("siteName") String siteName, @JsonProperty("siteCode") String siteCode,
                           @JsonProperty("address") Address address) {
        this.publisherId = publisherId;
        this.siteName = siteName;
        this.siteCode = siteCode;
        this.address = address;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
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

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateSiteInput createSiteInput = (CreateSiteInput) o;
        return getPublisherId().equals(createSiteInput.getPublisherId()) &&
                getSiteName().equals(createSiteInput.getSiteName()) &&
                getSiteCode().equals(createSiteInput.getSiteCode()) &&
                getAddress().equals(createSiteInput.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublisherId(), getSiteName(), getSiteCode(), getAddress());
    }

    @Override
    public String toString() {
        return "SiteInput{" +
                "publisherId='" + publisherId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", street='" + address + '\''  +
                '}';
    }
}
