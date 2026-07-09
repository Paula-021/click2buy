package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.ShippingOption;

public class ShippingOptionDTO {

    private String name;
    private Double price;
    private Long deliveryTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public ShippingOption toEntity() {
        ShippingOption shippingOption = new ShippingOption();
        shippingOption.setName(this.name);
        shippingOption.setPrice(this.price);
        shippingOption.setDeliveryTime(Math.toIntExact(this.deliveryTime));
        return shippingOption;
    }
}
