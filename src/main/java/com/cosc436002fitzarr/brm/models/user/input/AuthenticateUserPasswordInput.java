package com.cosc436002fitzarr.brm.models.user.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AuthenticateUserPasswordInput {
    private String userId;
    private String password;

    @JsonCreator
    public AuthenticateUserPasswordInput(@JsonProperty String userId, @JsonProperty String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticateUserPasswordInput that = (AuthenticateUserPasswordInput) o;
        return getUserId().equals(that.getUserId()) && getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getPassword());
    }

    @Override
    public String toString() {
        return "AuthenticateUserPasswordInput{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
