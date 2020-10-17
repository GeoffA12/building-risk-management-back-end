package com.cosc436002fitzarr.brm.models.user.input;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CreateUserInput {
    private SiteRole siteRole;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String password;

    @JsonCreator
    public CreateUserInput(@JsonProperty SiteRole siteRole, @JsonProperty String firstName, @JsonProperty String lastName,
                @JsonProperty String username, @JsonProperty String email, @JsonProperty String phone,
                @JsonProperty String password) {
        this.siteRole = siteRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String hashPassword) {
        this.password = hashPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserInput that = (CreateUserInput) o;
        return getSiteRole() == that.getSiteRole() &&
                getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getUsername().equals(that.getUsername()) &&
                getEmail().equals(that.getEmail()) &&
                getPhone().equals(that.getPhone()) &&
                getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSiteRole(), getFirstName(), getLastName(), getUsername(), getEmail(), getPhone(), getPassword());
    }

    @Override
    public String toString() {
        return "CreateUserInput{" +
                "siteRole=" + siteRole +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", hashPassword='" + password + '\'' +
                '}';
    }
}
