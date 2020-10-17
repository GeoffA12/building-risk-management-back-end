package com.cosc436002fitzarr.brm.models.user;


import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "user")
public class User implements Entity {
    private String id;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private List<EntityTrail> entityTrail;
    private String publisherId;
    private SiteRole siteRole;
    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String phone;
    private String authToken;
    private String hashPassword;
    // TODO: We'll worry about this after we finish base user setup and login/register stuff.
    // private List<String> associatedSiteIds;

    public User (String id, LocalDateTime updatedAt, LocalDateTime createdAt, List<EntityTrail> entityTrail, String publisherId,
                 SiteRole siteRole, String firstName, String lastName, String username, String email,
                 String phone, String authToken, String hashPassword){
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityTrail = entityTrail;
        this.publisherId = publisherId;
        this.siteRole = siteRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.authToken = authToken;
        this.hashPassword = hashPassword;
        // this.associatedSiteIds = associatedSiteIds;
    }

    public SiteRole getSiteRole() {
        return siteRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getHashPassword() {
        return hashPassword;
    }


    public void setSiteRole(SiteRole siteRole) {
        this.siteRole = siteRole;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId().equals(user.getId()) &&
                getUpdatedAt().equals(user.getUpdatedAt()) &&
                getCreatedAt().equals(user.getCreatedAt()) &&
                getEntityTrail().equals(user.getEntityTrail()) &&
                getPublisherId().equals(user.getPublisherId()) &&
                getSiteRole() == user.getSiteRole() &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getUsername().equals(user.getUsername()) &&
                getEmail().equals(user.getEmail()) &&
                getPhone().equals(user.getPhone()) &&
                getAuthToken().equals(user.getAuthToken()) &&
                getHashPassword().equals(user.getHashPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUpdatedAt(), getCreatedAt(), getEntityTrail(), getPublisherId(), getSiteRole(), getFirstName(), getLastName(), getUsername(), getEmail(), getPhone(), getAuthToken(), getHashPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", entityTrail=" + entityTrail +
                ", publisherId='" + publisherId + '\'' +
                ", siteRole=" + siteRole +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", authToken='" + authToken + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                '}';
    }
}
