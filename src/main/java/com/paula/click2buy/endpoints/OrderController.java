package com.paula.click2buy.endpoints;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.Order;
import com.paula.click2buy.domain.User;
import com.paula.click2buy.endpoints.dtos.OrderRequestDTO;
import com.paula.click2buy.endpoints.dtos.OrderResponseDTO;
import com.paula.click2buy.services.CartService;
import com.paula.click2buy.services.OrderService;
import com.paula.click2buy.services.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
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

    @PutMapping("/{id}/paid")
    public ResponseEntity<?> markOrderAsPaid(@PathVariable Long id) {
        orderService.markOrderAsPaid(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status") //prosseguir o status do pedido (1. PENDING_SHIPPING, 2. SHIPPED, 3. DELIVERED)
        public ResponseEntity<?> updateOrderStatus(@PathVariable Long id) {
            orderService.updateOrderStatus(id);
            return ResponseEntity.ok().build();
        }


    @PutMapping("/{id}/address") //atualizar o endereço do pedido
    public ResponseEntity<?> updateOrderAddress(@PathVariable Long id, @RequestBody Long idAddress) {
        orderService.updateOrderAddress(id, idAddress);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/cancel") //cancelar o pedido
     public ResponseEntity<?> updateOrderStatusCancel(@PathVariable Long id) {
        orderService.updateOrderStatusCancel(id);
        return ResponseEntity.ok().build();
    }



}
