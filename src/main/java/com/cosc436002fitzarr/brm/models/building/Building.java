package com.cosc436002fitzarr.brm.models.building;

import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.site.Address;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "building")
public class Building implements Entity {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EntityTrail> entityTrail;
    private String publisherId;
    private String name;
    private String code;
    private String siteId;
    private List<String> buildingRiskAssessmentIds;
    private Address address;

    public Building(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail, String publisherId,
                    String name, String code, String siteId, List<String> buildingRiskAssessmentIds, Address address) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityTrail = entityTrail;
        this.publisherId = publisherId;
        this.name = name;
        this.code = code;
        this.siteId = siteId;
        this.buildingRiskAssessmentIds = buildingRiskAssessmentIds;
        this.address = address;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public List<EntityTrail> getEntityTrail() {
        return entityTrail;
    }

    @Override
    public void setEntityTrail(List<EntityTrail> entityTrail) {
        this.entityTrail = entityTrail;
    }

    @Override
    public String getPublisherId() {
        return publisherId;
    }

    @Override
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<String> getBuildingRiskAssessmentIds() {
        return buildingRiskAssessmentIds;
    }

    public void setBuildingRiskAssessmentIds(List<String> buildingRiskAssessmentIds) {
        this.buildingRiskAssessmentIds = buildingRiskAssessmentIds;
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
        Building building = (Building) o;
        return Objects.equals(getId(), building.getId()) &&
                Objects.equals(getCreatedAt(), building.getCreatedAt()) &&
                Objects.equals(getUpdatedAt(), building.getUpdatedAt()) &&
                Objects.equals(getEntityTrail(), building.getEntityTrail()) &&
                Objects.equals(getPublisherId(), building.getPublisherId()) &&
                Objects.equals(getName(), building.getName()) &&
                Objects.equals(getCode(), building.getCode()) &&
                Objects.equals(getSiteId(), building.getSiteId()) &&
                Objects.equals(getBuildingRiskAssessmentIds(), building.getBuildingRiskAssessmentIds()) &&
                Objects.equals(getAddress(), building.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getEntityTrail(), getPublisherId(), getName(),
                getCode(), getSiteId(), getBuildingRiskAssessmentIds(), getAddress());
    }

    @Override
    public String toString() {
        return "Building{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", entityTrail=" + entityTrail +
                ", publisherId='" + publisherId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", siteId='" + siteId + '\'' +
                ", buildingRiskAssessmentIds=" + buildingRiskAssessmentIds +
                ", address=" + address +
                '}';
    }
}
