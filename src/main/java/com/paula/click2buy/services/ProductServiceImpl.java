package com.paula.click2buy.services;

import com.paula.click2buy.domain.Product;
import com.paula.click2buy.endpoints.dtos.ProductUpdateRequestDTO;
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
    public void updateProduct(Long id, ProductUpdateRequestDTO productUpdateRequestDTO) {

        Product product = getProductById(id);

        if(!productUpdateRequestDTO.getName().isEmpty()){
            product.setName(productUpdateRequestDTO.getName());
        }
        if(!productUpdateRequestDTO.getDescription().isEmpty()){
            product.setDescription(productUpdateRequestDTO.getDescription());
        }
        if(!(productUpdateRequestDTO.getPrice() <= 0.0)){
            product.setPrice(productUpdateRequestDTO.getPrice());
        }
        if(!(productUpdateRequestDTO.getStockQuantity() < 0)){
            product.setStockQuantity(productUpdateRequestDTO.getStockQuantity());
        }

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
