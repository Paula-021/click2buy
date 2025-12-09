package com.paula.click2buy.endpoints;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.Order;
import com.paula.click2buy.domain.User;
import com.paula.click2buy.endpoints.dtos.OrderRequestDTO;
import com.paula.click2buy.endpoints.dtos.OrderResponseDTO;
import com.paula.click2buy.services.CartService;
import com.paula.click2buy.services.OrderService;
import com.paula.click2buy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        // Lógica para criar o pedido com base no orderRequestDTO
        Cart cart = cartService.getCartById(orderRequestDTO.getIdCart());
        User user = userService.getUserById(orderRequestDTO.getIdUser());

        Order order = orderRequestDTO.toEntity(cart, user);

        orderService.addOrder(order);

        return ResponseEntity.ok().build();
    }

    //somente admin
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        List<OrderResponseDTO> ordersResponseDTO = orders.stream().map(OrderResponseDTO::new).toList();

        return ResponseEntity.ok().body(ordersResponseDTO);
    }
}
