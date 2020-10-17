package com.cosc436002fitzarr.brm.models.user.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ReferenceInput {
    private String id;

    @JsonCreator
    public ReferenceInput(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.cosc436002fitzarr.brm.models.user.input.ReferenceInput that = (com.cosc436002fitzarr.brm.models.user.input.ReferenceInput) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ReferenceInput{" +
                "id='" + id + '\'' +
                '}';
    }
}