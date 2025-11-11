package com.paula.click2buy.services;

import com.paula.click2buy.domain.ItemCart;
import com.paula.click2buy.exceptions.ItemCartNotFoundException;
import com.paula.click2buy.repositories.ItemCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ItemCartServiceImpl implements ItemCartService {

    @Autowired
    private ItemCartRepository itemCartRepository;

    @Override
    public ItemCart addItemCart(ItemCart itemCart) {
        return itemCartRepository.save(itemCart);
    }

    @Override
    public void updateItemCart(ItemCart itemCart) {
        itemCartRepository.save(itemCart);
    }

    @Override
    public void deleteItemCart(Long id) {
        getItemCartById(id);
        itemCartRepository.deleteById(id);
    }

    @Override
    public ItemCart getItemCartById(Long id) {
       return itemCartRepository.findById(id).orElseThrow(()->  new ItemCartNotFoundException());
    }

    @Override
    public List<ItemCart> getAllItemCarts() {
        List<ItemCart> itemCarts = itemCartRepository.findAll();
        return itemCarts;
    }
}
