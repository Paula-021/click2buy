package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.ItemCart;
import com.paula.click2buy.domain.Product;
import com.paula.click2buy.services.ProductService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartRequestDTO {
    //@NotEmpty(message = "The cart must have at least one item")
    private List<ItemCartRequestDTO> listItemCartDTO;



    public List<ItemCartRequestDTO> getListItemCartDTO() {

        return listItemCartDTO;
    }

    public void setListItemCartDTO(List<ItemCartRequestDTO> listItemCartDTO) {

        this.listItemCartDTO = listItemCartDTO;
    }


}
