package com.cosc436002fitzarr.brm.models.site.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class GetSitesByNameInput {
    private String siteName;

    @JsonCreator
    public GetSitesByNameInput(@JsonProperty("siteName") String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetSitesByNameInput that = (GetSitesByNameInput) o;
        return getSiteName().equals(that.getSiteName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSiteName());
    }

    @Override
    public String toString() {
        return "GetSiteByNameInput{" +
                "siteName='" + siteName + '\'' +
                '}';
    }
}
