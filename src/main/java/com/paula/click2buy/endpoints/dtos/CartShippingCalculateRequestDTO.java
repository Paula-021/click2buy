package com.paula.click2buy.endpoints.dtos;

public class CartShippingCalculateRequestDTO {
    private String zipCodeSender;
    private String zipCodeRecipient;

    public String getZipCodeSender() {
        return zipCodeSender;
    }

    public void setZipCodeSender(String zipCodeSender) {
        this.zipCodeSender = zipCodeSender;
    }

    public String getZipCodeRecipient() {
        return zipCodeRecipient;
    }

    public void setZipCodeRecipient(String zipCodeRecipient) {
        this.zipCodeRecipient = zipCodeRecipient;
    }
}
