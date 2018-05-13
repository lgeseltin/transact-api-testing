package com.lgeseltins.backend.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddressDto {
    @NotNull
    @NotEmpty
    private String country;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String state;

    @NotNull
    @NotEmpty
    private String zip;

    @NotNull
    @NotEmpty

    private String street;


    public AddressDto(String country, String city, String state, String zip, String street) {
        this.country = country;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

}
