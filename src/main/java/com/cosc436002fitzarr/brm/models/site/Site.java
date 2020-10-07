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
    // Whenever an admin creates a new site, we'll initially add the publisherId to the site first. Every subsequent user in the system will be added to this list
    // when they register an account at a new site or an admin grants them permission to the site.
    // TODO: Integrate with User class. (Given a list of ID's for the users at a site, we should be able to call the User service and request a list of users back.)
    // private List<String> userIds;

    public Site(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail,
                String publisherId, String siteName, String siteCode, Address address) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityTrail = entityTrail;
        this.publisherId = publisherId;
        this.siteName = siteName;
        this.siteCode = siteCode;
        this.address = address;
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
                getAddress().equals(site.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(), getSiteName(), getSiteCode(), getAddress());
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
                '}';
    }
}
