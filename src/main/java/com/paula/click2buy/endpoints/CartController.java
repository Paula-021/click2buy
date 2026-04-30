package com.paula.click2buy.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.ShippingOption;
import com.paula.click2buy.endpoints.dtos.CartResponseDTO;
import com.paula.click2buy.endpoints.dtos.CartRequestDTO;
import com.paula.click2buy.endpoints.dtos.CartShippingCalculateRequestDTO;
import com.paula.click2buy.endpoints.dtos.ShippingOptionDTO;
import com.paula.click2buy.services.CartService;
import com.paula.click2buy.shipment.dtos.ShipmentResponseDTO;
import com.paula.click2buy.shipment.services.MelhorEnvioShipmentCalculateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MelhorEnvioShipmentCalculateService melhorEnvioShipmentCalculateService;

    @PostMapping
    public ResponseEntity<?> addCart() { // a única função dele será abrir um carrinho (sem itens)
        Cart cart = cartService.addCart();
        return ResponseEntity.status(201).body(cart.getId());//201 CREATED
    }

    @PutMapping("/{cartId}/items") // PUT /carts/1/items body: [{item1: productId, quantity}, {item2}, ...]
    public ResponseEntity<?> addItemsToCart(@PathVariable Long cartId, @Valid @RequestBody CartRequestDTO cartRequestDTO) {

        Cart cart = cartService.addItemsToCart(cartId, cartRequestDTO);

        return ResponseEntity.status(200).body(new CartResponseDTO(cart));//200
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<?> removeOneItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        Cart cart = cartService.removeOneItemFromTheCart(cartId, itemId);

        return ResponseEntity.status(200).body(new CartResponseDTO(cart));//200
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        Cart cart = cartService.removeItemFromCart(cartId, itemId);

        return ResponseEntity.status(200).body(new CartResponseDTO(cart));//200
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
        List<Cart> carts = cartService.getAllCarts();
        List<CartResponseDTO> cartResponseDTOs = carts.stream().map(CartResponseDTO::new).toList();
        return ResponseEntity.ok().body(cartResponseDTOs);//200 OK
    }

    @PostMapping("/{cartId}/shipping/calculate")
    public ResponseEntity<?> calculateShipping(@PathVariable Long cartId, @RequestBody CartShippingCalculateRequestDTO cartShippingCalculateRequestDTO) throws JsonProcessingException {
        Cart cart = cartService.getCartById(cartId);

        List<ShipmentResponseDTO> shippingOptions = cartService.calculateShipping(cart, cartShippingCalculateRequestDTO);
        return ResponseEntity.ok().body(shippingOptions);//200 OK
    }

    @PutMapping("/{cartId}/shipping/select")
    public ResponseEntity<?> selectShippingOption(@PathVariable Long cartId, @RequestBody ShippingOptionDTO shippingOptionDTO) throws JsonProcessingException {
        Cart cart = cartService.getCartById(cartId);
        ShippingOption shippingOption = shippingOptionDTO.toEntity();
        cart.setShippingSelected(shippingOption);

        cartService.updateCart(cart);
        return ResponseEntity.ok().body("Cart updated successfully!");//200 OK
    }

    @PutMapping("/{cartId}/calculateTotalPrice")
    public ResponseEntity<?> calculateTotalPrice(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        Double totalPrice = cartService.calculateTotalPrice(cart);

        if(cart.getTotalPrice() == null || !cart.getTotalPrice().equals(totalPrice)){
            cart.setTotalPrice(totalPrice);
            cartService.updateCart(cart);

        }

        return ResponseEntity.ok().body(totalPrice);//200 OK
    }


    @GetMapping("/{cartId}/checkout")
    public ResponseEntity<?> checkoutCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);

        if (cart.getShippingSelected() == null) {
            return ResponseEntity.badRequest().body("Please select a shipping option before checkout!");//400 BAD REQUEST
        }

        //aqui poderia ter uma integração com um serviço de pagamento, por exemplo, para processar o pagamento do carrinho

        return ResponseEntity.ok().body("Checkout completed successfully!");//200 OK
    }


}
