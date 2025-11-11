package com.paula.click2buy.services;

import com.paula.click2buy.domain.ItemCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemCartService {
    ItemCart addItemCart(ItemCart itemCart);

    void updateItemCart(ItemCart itemCart);

    void deleteItemCart(Long id);

    ItemCart getItemCartById(Long id);

    List<ItemCart> getAllItemCarts();
}
