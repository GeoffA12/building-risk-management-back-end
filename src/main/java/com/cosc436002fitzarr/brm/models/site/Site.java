package com.cosc436002fitzarr.brm.models.site;

import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "site")
public class Site implements Entity {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EntityTrail> entityTrail;
    private String publisherId;
    private String siteName;
    private String siteCode;
    private Address address;
    private List<String> userIds;

    public Site(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail,
                String publisherId, String siteName, String siteCode, Address address, List<String> userIds) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityTrail = entityTrail;
        this.publisherId = publisherId;
        this.siteName = siteName;
        this.siteCode = siteCode;
        this.address = address;
        this.userIds = userIds;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public List<EntityTrail> getEntityTrail() {
        return entityTrail;
    }

    @Override
    public String getPublisherId() {
        return publisherId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public void setEntityTrail(List<EntityTrail> entityTrail) {
        this.entityTrail = entityTrail;
    }

    @Override
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return getId().equals(site.getId()) &&
                getCreatedAt().equals(site.getCreatedAt()) &&
                getUpdatedAt().equals(site.getUpdatedAt()) &&
                getEntityTrail().equals(site.getEntityTrail()) &&
                getPublisherId().equals(site.getPublisherId()) &&
                getSiteName().equals(site.getSiteName()) &&
                getSiteCode().equals(site.getSiteCode()) &&
                getAddress().equals(site.getAddress()) &&
                getUserIds().equals(site.getUserIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(), getSiteName(), getSiteCode(), getAddress(), getUserIds());
    }

    @Override
    public String toString() {
        return "Site{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", entityTrail=" + entityTrail +
                ", publisherId='" + publisherId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", address=" + address +
                ", userIds=" + userIds +
                '}';
    }
}
