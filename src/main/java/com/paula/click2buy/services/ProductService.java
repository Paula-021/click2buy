package com.paula.click2buy.services;

import com.paula.click2buy.domain.Product;
import com.paula.click2buy.endpoints.dtos.ProductUpdateRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface ProductService {
    Product addProduct(Product product);

    void updateProduct(Long id, ProductUpdateRequestDTO productUpdateRequestDTO);

    void deleteProduct(Long id);

    Product getProductById(Long id);

    List<Product> getAllProducts();
}
