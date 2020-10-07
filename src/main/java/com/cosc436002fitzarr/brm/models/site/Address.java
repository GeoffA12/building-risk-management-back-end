package com.cosc436002fitzarr.brm.models.site;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

// TODO: Consider making a separate input class for this, as we both require Address as API Input and persist the Address model in the site repository.
public class Address {
    private String street;
    private String city;
    // TODO: Should this be an enumerated type? I.e. Do we want to tightly control address input?
    private String state;

    @JsonCreator
    public Address(@JsonProperty("street") String street, @JsonProperty("city") String city, @JsonProperty("state") String state) {
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return getStreet().equals(address.getStreet()) &&
                getCity().equals(address.getCity()) &&
                getState().equals(address.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getCity(), getState());
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
