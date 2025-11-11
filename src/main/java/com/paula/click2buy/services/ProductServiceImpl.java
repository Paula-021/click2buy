package com.paula.click2buy.services;

import com.paula.click2buy.domain.Product;
import com.paula.click2buy.exceptions.ProductNotFoundException;
import com.paula.click2buy.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(Long id) {
        getProductById(id);
        productRepository.deleteById(id);

    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException());

    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }
}
