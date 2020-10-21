package com.cosc436002fitzarr.brm.models.user.input;

import com.cosc436002fitzarr.brm.models.PageInput;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetAllUsersBySiteInput {
    private PageInput pageInput;
    private List<String> siteIds;


    @JsonCreator
    public GetAllUsersBySiteInput(@JsonProperty PageInput pageInput, @JsonProperty List<String> siteIds) {
        this.pageInput = pageInput;
        this.siteIds = siteIds;
    }

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
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
        GetAllUsersBySiteInput that = (GetAllUsersBySiteInput) o;
        return getPageInput().equals(that.getPageInput()) &&
                getSiteIds().equals(that.getSiteIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageInput(), getSiteIds());
    }

    @Override
    public String toString() {
        return "GetAllUsersBySite{" +
                "pageInput=" + pageInput +
                ", siteIds=" + siteIds +
                '}';
    }
}
