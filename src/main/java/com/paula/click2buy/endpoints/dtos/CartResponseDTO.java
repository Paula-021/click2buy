package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.ItemCart;

import java.util.List;

public class CartResponseDTO {
    private List<ItemCartResponseDTO> listItemCartDTO;


    public List<ItemCartResponseDTO> getListItemCartDTO() {
        return listItemCartDTO;
    }

    public void setListItemCartDTO(List<ItemCartResponseDTO> listItemCartDTO) {
        this.listItemCartDTO = listItemCartDTO;
    }

    public CartResponseDTO(Cart cart) {
        this.listItemCartDTO = cart.getListItemCart().stream()
                .map(ItemCartResponseDTO::new)
                .toList();
    }

}
