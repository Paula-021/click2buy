package com.paula.click2buy.endpoints;


import com.paula.click2buy.domain.Product;
import com.paula.click2buy.endpoints.dtos.ProductRequestDTO;
import com.paula.click2buy.endpoints.dtos.ProductResponseDTO;
import com.paula.click2buy.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        Product product = productService.addProduct(productRequestDTO.toEntity());
        return ResponseEntity.status(201).body(product);//201 CREATED

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO, @PathVariable Long id) {
        Product product = productRequestDTO.toEntity();
        product.setId(id);
        productService.updateProduct(product);
        return ResponseEntity.ok().body("Product updated successfully!");//200 OK
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Product deleted successfully!");//200 OK
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(product);
        return ResponseEntity.ok().body(productResponseDTO);//200 OK
    }
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDTO> productResponseDTOs = products.stream().map(ProductResponseDTO::new).toList();
        return ResponseEntity.ok().body(productResponseDTOs);//200 OK
    }


}
