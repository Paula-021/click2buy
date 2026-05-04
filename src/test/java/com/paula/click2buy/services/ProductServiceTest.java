package com.paula.click2buy.services;

import com.paula.click2buy.domain.Product;
import com.paula.click2buy.exceptions.ProductNotFoundException;
import com.paula.click2buy.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldAddProduct() {
    // Deve adicionar um produto e retornar o produto salvo

        // Arrange
        Product product = new Product();
        product.setName("Produto 1");

        //quando o método save do productRepository for chamado com qualquer objeto do tipo Product, ele deve retornar o mesmo objeto Product que foi passado como parâmetro.
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        //chamar o método addProduct do productService com o produto criado e armazenar o resultado em uma variável chamada savedProduct.
        Product savedProduct = productService.addProduct(product);

        // Assert
        //verificar se o produto salvo é igual ao produto que foi passado como parâmetro
        assertEquals(product, savedProduct);
        verify(productRepository).save(product);

    }
    @Test
        // Deve atualizar um produto existente
    void shouldUpdateProduct() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Produto 1");

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        productService.updateProduct(product);

        // Assert
        verify(productRepository).save(product);

    }

    
    @Test
    //Deve chamar o método deleteById ao deletar um Produto existente
    void shouldDeleteExistingProduct() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository).deleteById(productId);
    }
    //Deve chamar o método findById ao buscar um produto por ID
    @Test
    void shouldReturnProductWhenExists() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product foundProduct = productService.getProductById(productId);

        // Assert
        assertEquals(product, foundProduct);
        verify(productRepository).findById(productId);
    }
    @Test
    void shouldThrowExceptionWhenProductNotFound(){
        // Deve lançar ProductNotFoundException quando o produto não for encontrado

        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository).findById(productId);
    }

    @Test
    //Deve chamar o método findAll ao buscar todos os produtos
    void shouldFindAllProduct(){
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // Act

        List<Product> products = productService.getAllProducts();

        // Assert
        assertEquals(2, products.size());
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));
        verify(productRepository).findAll();
    }


}
