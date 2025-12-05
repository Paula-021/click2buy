package com.paula.click2buy.endpoints;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.Order;
import com.paula.click2buy.domain.User;
import com.paula.click2buy.endpoints.dtos.OrderRequestDTO;
import com.paula.click2buy.services.CartService;
import com.paula.click2buy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        // Lógica para criar o pedido com base no orderRequestDTO
        Cart cart = cartService.getCartById(orderRequestDTO.getIdCart());
        User user = userService.getUserById(orderRequestDTO.getIdUser());

        Order order = orderRequestDTO.toEntity(cart, user);



        return ResponseEntity.ok().build();
    }
}
