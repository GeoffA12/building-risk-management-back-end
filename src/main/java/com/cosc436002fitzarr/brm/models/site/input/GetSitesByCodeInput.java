package com.cosc436002fitzarr.brm.models.site.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class GetSitesByCodeInput {
    private String siteCode;

    @JsonCreator
    public GetSitesByCodeInput(@JsonProperty String siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetSitesByCodeInput that = (GetSitesByCodeInput) o;
        return getSiteCode().equals(that.getSiteCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSiteCode());
    }

    @Override
    public String toString() {
        return "GetSitesByCodeInput{" +
                "siteCode='" + siteCode + '\'' +
                '}';
    }
}
