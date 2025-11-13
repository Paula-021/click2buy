package com.paula.click2buy.services;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.endpoints.dtos.CartRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    Cart addCart(CartRequestDTO cartRequestDTO);

    void updateCart(Cart cart);

    void deleteCart(Long id);

    Cart getCartById(Long id);

    List<Cart> getAllCarts();
}
