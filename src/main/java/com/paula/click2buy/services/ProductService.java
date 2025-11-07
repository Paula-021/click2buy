package com.paula.click2buy.services;

import com.paula.click2buy.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface ProductService {
    Product addProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Long id);

    Product getProductById(Long id);

    List<Product> getAllProducts();
}
