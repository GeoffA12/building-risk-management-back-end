package com.cosc436002fitzarr.brm.models.riskassessment.input;

import com.cosc436002fitzarr.brm.models.PageInput;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetAllRiskAssessmentsBySiteInput {
    private PageInput pageInput;
    private List<String> associatedSiteIds;

    @JsonCreator
    public GetAllRiskAssessmentsBySiteInput(@JsonProperty PageInput pageInput, @JsonProperty List<String> associatedSiteIds, @JsonProperty String userId) {
        this.pageInput = pageInput;
        this.associatedSiteIds = associatedSiteIds;
    }

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
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
        GetAllRiskAssessmentsBySiteInput that = (GetAllRiskAssessmentsBySiteInput) o;
        return getPageInput().equals(that.getPageInput()) &&
                getAssociatedSiteIds().equals(that.getAssociatedSiteIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageInput(), getAssociatedSiteIds());
    }

    @Override
    public String toString() {
        return "GetAllRiskAssessmentsBySiteInput{" +
                "pageInput=" + pageInput +
                ", associatedSiteIds=" + associatedSiteIds +
                '}';
    }
}
