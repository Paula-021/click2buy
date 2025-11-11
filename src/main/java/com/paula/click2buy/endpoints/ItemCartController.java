package com.paula.click2buy.endpoints;

import com.paula.click2buy.domain.ItemCart;
import com.paula.click2buy.domain.Product;
import com.paula.click2buy.endpoints.dtos.ItemCartResponseDTO;
import com.paula.click2buy.endpoints.dtos.ItemCartRequestDTO;
import com.paula.click2buy.endpoints.dtos.ItemCartUpdateRequestDTO;
import com.paula.click2buy.services.ItemCartService;
import com.paula.click2buy.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-carts")
public class ItemCartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ItemCartService itemCartService;

    @PostMapping //possivelmente vamos apagar
    public ResponseEntity<?> addItemCart(@Valid @RequestBody ItemCartRequestDTO itemCartRequestDTO) {
        Long idProduct = itemCartRequestDTO.getProductId();
        Product product = productService.getProductById(idProduct);
        ItemCart itemCart = itemCartRequestDTO.toEntity(product);
        itemCartService.addItemCart(itemCart);
        return ResponseEntity.status(201).body("ItemCart added successfully!");//201 CREATED
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItemCart(@PathVariable Long id, @Valid @RequestBody ItemCartUpdateRequestDTO itemCartUpdateRequestDTO) {
        ItemCart itemCart = itemCartService.getItemCartById(id);
        itemCart.setQuantity(itemCartUpdateRequestDTO.getQuantity());
        itemCartService.updateItemCart(itemCart);
        return ResponseEntity.ok("ItemCart updated successfully!");//200 OK
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemCart(@PathVariable Long id) {
        itemCartService.deleteItemCart(id);
        return ResponseEntity.ok().body("ItemCart deleted successfully!");//200 OK
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemCartById(@PathVariable Long id) {
        ItemCart itemCart = itemCartService.getItemCartById(id);
        ItemCartResponseDTO itemCartResponseDTO = new ItemCartResponseDTO(itemCart);
        return ResponseEntity.ok().body(itemCartResponseDTO);//200 OK

    }
    @GetMapping
    public ResponseEntity<?> getAllItemCarts() { //analisar se dá pra tirar
        List<ItemCart> itemCarts = itemCartService.getAllItemCarts();
        List<ItemCartResponseDTO> itemCartResponseDTOs = itemCarts.stream().map(ItemCartResponseDTO::new).toList();
        return ResponseEntity.ok().body(itemCartResponseDTOs);//200 OK
    }

}
