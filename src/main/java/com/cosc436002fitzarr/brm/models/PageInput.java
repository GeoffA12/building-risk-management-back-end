package com.cosc436002fitzarr.brm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PageInput {
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private Sort.Direction sortDirection;

    public static final Integer DEFAULT_PAGE_SIZE = 50;
    public static final Integer DEFAULT_PAGE_NO = 0;

    @JsonCreator
    public PageInput(@JsonProperty Integer pageNo, @JsonProperty Integer pageSize, @JsonProperty String sortBy, @JsonProperty Sort.Direction sortDirection) {
        this.pageNo = (pageNo == null) ? DEFAULT_PAGE_NO : pageNo;
        this.pageSize = (pageSize == null) ? DEFAULT_PAGE_SIZE : pageSize;
        this.sortBy = (sortBy == null) ? "id" : sortBy;
        this.sortDirection = (sortDirection == null) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageInput pageInput = (PageInput) o;
        return getPageNo().equals(pageInput.getPageNo()) &&
                getPageSize().equals(pageInput.getPageSize()) &&
                getSortBy().equals(pageInput.getSortBy()) &&
                getSortDirection().equals(pageInput.getSortDirection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageNo(), getPageSize(), getSortBy(), getSortDirection());
    }

    @Override
    public String toString() {
        return "PageInput{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                ", sortDirection=" + sortDirection +
                '}';
    }
}
