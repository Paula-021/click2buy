package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AddressRequestDTO {

    @NotBlank(message = "Zipcode is required")
    @Pattern(regexp = "\\d{8}", message = "zipcode must contain exactly 8 digits")
    private String zipcode;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message = "Number is required")
    private String number;
    private String complement;
    @NotBlank(message = "District is required")
    private String district;
    @NotBlank(message = "State is required")
    private String state;
    @NotBlank(message = "Country is required")
    private String country;

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public Address toEntity() {
        Address address = new Address();
        address.setZipcode(this.zipcode);
        address.setStreet(this.street);
        address.setNumber(this.number);
        address.setComplement(this.complement);
        address.setDistrict(this.district);
        address.setState(this.state);
        address.setCountry(this.country);
        return address;
    }
}
