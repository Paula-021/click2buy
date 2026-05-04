package com.paula.click2buy.shipment.dtos;

import java.util.List;

public class ShipmentRequestDTO {

    private String sender;
    private String recipient;
    private List<MelhorEnvioProductDTO> products;

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

    public List<MelhorEnvioProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<MelhorEnvioProductDTO> products) {
        this.products = products;
    }
}
