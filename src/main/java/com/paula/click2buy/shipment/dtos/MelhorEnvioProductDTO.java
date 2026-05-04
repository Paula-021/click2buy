package com.paula.click2buy.shipment.dtos;

import com.paula.click2buy.domain.Product;

public class MelhorEnvioProductDTO {

    private Product product;
    private Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
