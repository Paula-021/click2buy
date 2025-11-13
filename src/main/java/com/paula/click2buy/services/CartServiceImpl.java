package com.paula.click2buy.services;


import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.ItemCart;
import com.paula.click2buy.domain.Product;
import com.paula.click2buy.endpoints.dtos.CartRequestDTO;
import com.paula.click2buy.exceptions.CartNotFoundException;
import com.paula.click2buy.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Cart addCart(CartRequestDTO cartRequestDTO) {
        List<ItemCart> listItemCart = cartRequestDTO.getListItemCartDTO().stream()
                .map(itemCartDTO -> {
                            Product product = productService.getProductById(itemCartDTO.getProductId());

                            return itemCartDTO.toEntity(product);
                        }
                )
                .toList();
        Cart cart = new Cart();
        cart.setListItemCart(listItemCart);

        return cartRepository.save(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(()-> new CartNotFoundException());
    }

    @Override
    public List<Cart> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts;
    }
}
