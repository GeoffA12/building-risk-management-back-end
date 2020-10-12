package com.cosc436002fitzarr.brm.models.user;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private SiteRole siteRole;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String hashPassword;
    private List<String> associatedSiteIds;

    public User (SiteRole siteRole, String firstName, String lastName, String username, String email,
                 String phone, String hashPassword, List<String> associatedSiteIds){
        this.siteRole = siteRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.hashPassword = hashPassword;
        this.associatedSiteIds = associatedSiteIds;
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

    public String getHashPassword() {
        return hashPassword;
    }

    public List<String> getAssociatedSiteIds() {
        return associatedSiteIds;
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

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setAssociatedSiteIds(List<String> associatedSiteIds) {
        this.associatedSiteIds = associatedSiteIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getSiteRole() == user.getSiteRole() &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPhone(), user.getPhone()) &&
                Objects.equals(getHashPassword(), user.getHashPassword()) &&
                Objects.equals(getAssociatedSiteIds(), user.getAssociatedSiteIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSiteRole(), getFirstName(), getLastName(), getUsername(), getEmail(), getPhone(), getHashPassword(), getAssociatedSiteIds());
    }

    @Override
    public String toString() {
        return "User{" +
                "siteRole=" + siteRole +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", associatedSiteIds=" + associatedSiteIds +
                '}';
    }
}
