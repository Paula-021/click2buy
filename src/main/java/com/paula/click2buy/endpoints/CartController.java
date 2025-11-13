package com.paula.click2buy.endpoints;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.endpoints.dtos.CartResponseDTO;
import com.paula.click2buy.endpoints.dtos.CartRequestDTO;
import com.paula.click2buy.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<?> addCart(@Valid @RequestBody CartRequestDTO cartRequestDTO) {
        Cart cart = cartService.addCart(cartRequestDTO);
        return ResponseEntity.status(201).body(cart);//201 CREATED
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@Valid @RequestBody CartRequestDTO cartRequestDTO, @PathVariable Long id) {
        //Cart cart = cartRequestDTO.toEntity();
        //cart.setId(id);
        //cartService.updateCart(cart);
        return ResponseEntity.ok().body("Cart updated successfully!");//200 OK
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok().body("Cart deleted successfully!");//200 OK
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        CartResponseDTO cartResponseDTO = new CartResponseDTO(cart);
        return ResponseEntity.ok().body(cartResponseDTO);//200 OK
    }
    @GetMapping
    public ResponseEntity<?> getAllCarts() {
        List <Cart> carts = cartService.getAllCarts();
        List<CartResponseDTO> cartResponseDTOs = carts.stream().map(CartResponseDTO::new).toList();
        return ResponseEntity.ok().body(cartResponseDTOs);//200 OK
    }

}
