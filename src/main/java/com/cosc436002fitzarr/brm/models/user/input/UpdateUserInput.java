package com.cosc436002fitzarr.brm.models.user.input;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class UpdateUserInput {
    private String id;
    private String userId;
    private SiteRole siteRole;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private List<String> siteIds;

    @JsonCreator
    public UpdateUserInput(@JsonProperty String id, @JsonProperty String userId, @JsonProperty SiteRole siteRole, @JsonProperty String firstName,
                           @JsonProperty String lastName, @JsonProperty String username, @JsonProperty String email,
                           @JsonProperty String phone, @JsonProperty List<String> siteIds) {
        this.id = id;
        this.userId = userId;
        this.siteRole = siteRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.siteIds = siteIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SiteRole getSiteRole() {
        return siteRole;
    }

    public void setSiteRole(SiteRole siteRole) {
        this.siteRole = siteRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        UpdateUserInput that = (UpdateUserInput) o;
        return getId().equals(that.getId()) &&
                getUserId().equals(that.getUserId()) &&
                getSiteRole() == that.getSiteRole() &&
                getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getUsername().equals(that.getUsername()) &&
                getEmail().equals(that.getEmail()) &&
                getPhone().equals(that.getPhone()) &&
                getSiteIds().equals(that.getSiteIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getSiteRole(), getFirstName(), getLastName(), getUsername(), getEmail(),
                getPhone(), getSiteIds());
    }

    @Override
    public String toString() {
        return "UpdateUserInput{" +
                "id=" + id +
                "userid=" + userId +
                "siteRole=" + siteRole + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", siteIds='" + siteIds + '\'' +
                '}';
    }
}
