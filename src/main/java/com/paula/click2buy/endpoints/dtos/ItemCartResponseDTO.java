package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.ItemCart;
import com.paula.click2buy.domain.Product;

public class ItemCartResponseDTO {
    private Product product;
    private Integer quantity;

    public ItemCartResponseDTO(ItemCart itemCart) {
        this.product = itemCart.getProduct();
        this.quantity = itemCart.getQuantity();
    }

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
