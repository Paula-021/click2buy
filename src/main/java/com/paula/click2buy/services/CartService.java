package com.paula.click2buy.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.endpoints.dtos.CartRequestDTO;
import com.paula.click2buy.endpoints.dtos.CartShippingCalculateRequestDTO;
import com.paula.click2buy.shipment.dtos.ShipmentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    
    Cart addCart();

    void updateCart(Cart cart);

    void deleteCart(Long id);

    Cart getCartById(Long id);

    List<Cart> getAllCarts();

    Cart addItemsToCart(Long cartId, @Valid CartRequestDTO cartRequestDTO);

    Cart removeOneItemFromTheCart(Long cartId, Long itemId);

    Cart removeItemFromCart(Long cartId, Long itemId);

    List<ShipmentResponseDTO> calculateShipping(Cart cart, CartShippingCalculateRequestDTO cartShippingCalculateRequestDTO) throws JsonProcessingException;

    Double calculateTotalPrice(Cart cart);
}
