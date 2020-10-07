package com.cosc436002fitzarr.brm.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class EntityTrail {
    private LocalDateTime timestamp;
    private String userId;
    private String systemComment;

    public EntityTrail(LocalDateTime timestamp, String userId, String systemComment) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.systemComment = systemComment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSystemComment() {
        return systemComment;
    }

    public void setSystemComment(String systemComment) {
        this.systemComment = systemComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityTrail that = (EntityTrail) o;
        return getTimestamp().equals(that.getTimestamp()) &&
                getUserId().equals(that.getUserId()) &&
                getSystemComment().equals(that.getSystemComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimestamp(), getUserId(), getSystemComment());
    }

    @Override
    public String toString() {
        return "EntityTrail{" +
                "timestamp=" + timestamp +
                ", userId='" + userId + '\'' +
                ", systemComment='" + systemComment + '\'' +
                '}';
    }
}
