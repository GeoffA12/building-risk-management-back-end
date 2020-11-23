package com.cosc436002fitzarr.brm.models.building.input;

import com.cosc436002fitzarr.brm.models.site.Address;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class UpdateBuildingInput {
    private String id;
    private String publisherId;
    private String name;
    private String code;
    private String siteId;
    private Address address;

    @JsonCreator
    public UpdateBuildingInput(@JsonProperty String id, @JsonProperty String publisherId, @JsonProperty String name,
                               @JsonProperty String code, @JsonProperty String siteId, @JsonProperty Address address) {
        this.id = id;
        this.publisherId = publisherId;
        this.name = name;
        this.code = code;
        this.siteId = siteId;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisherId() {
        return publisherId;
    }

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
        UpdateBuildingInput that = (UpdateBuildingInput) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPublisherId(), that.getPublisherId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getSiteId(), that.getSiteId()) &&
                Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPublisherId(), getName(), getCode(), getSiteId(), getAddress());
    }

    @Override
    public String toString() {
        return "UpdateBuildingInput{" +
                "id='" + id + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", siteId='" + siteId + '\'' +
                ", address=" + address +
                '}';
    }
}
