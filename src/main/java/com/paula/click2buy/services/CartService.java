package com.paula.click2buy.services;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.endpoints.dtos.CartRequestDTO;
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
}
