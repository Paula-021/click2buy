package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.ItemCart;

import java.util.List;

public class CartResponseDTO {
    private List<ItemCart> listItemCart;

    public List<ItemCart> getListItemCart() {
        return listItemCart;
    }

    public void setListItemCart(List<ItemCart> listItemCart) {
        this.listItemCart = listItemCart;
    }
    public CartResponseDTO(Cart cart) {
        this.listItemCart = cart.getListItemCart();
    }
}
