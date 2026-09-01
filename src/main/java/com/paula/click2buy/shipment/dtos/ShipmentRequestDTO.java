package com.paula.click2buy.shipment.dtos;

import java.util.List;

public class ShipmentRequestDTO {

    private String sender;
    private String recipient;
    private List<SuperFreteProductDTO> products;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public List<SuperFreteProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<SuperFreteProductDTO> products) {
        this.products = products;
    }
}
