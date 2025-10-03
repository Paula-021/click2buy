package com.paula.click2buy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private List<ItemCart> listItemCart ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ItemCart> getListItemCart() {
        return listItemCart;
    }

    public void setListItemCart(List<ItemCart> listItemCart) {
        this.listItemCart = listItemCart;
    }
}
