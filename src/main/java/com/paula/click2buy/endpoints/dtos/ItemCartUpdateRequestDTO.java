package com.paula.click2buy.endpoints.dtos;

import jakarta.validation.constraints.Min;

public class ItemCartUpdateRequestDTO {

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public ItemCartUpdateRequestDTO() {
    }

    public ItemCartUpdateRequestDTO(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
