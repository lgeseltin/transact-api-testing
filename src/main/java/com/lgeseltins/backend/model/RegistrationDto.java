package com.lgeseltins.backend.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegistrationDto {
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String phone;

    @NotNull
    @NotEmpty
    private String pwd;

    @NotNull
    @NotEmpty
    private String birthDate;

    private String description;

    private AddressDto address;

    public RegistrationDto(String email, String phone, String pwd, String birthDate, String description,
                           AddressDto address) {
        this.email = email;
        this.phone = phone;
        this.pwd = pwd;
        this.birthDate = birthDate;
        this.description = description;
        this.address = address;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
