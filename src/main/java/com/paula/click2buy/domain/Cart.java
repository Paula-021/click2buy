package com.paula.click2buy.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<ItemCart> listItemCart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemCart> getListItemCart() {
        return listItemCart;
    }

    public void setListItemCart(List<ItemCart> listItemCart) {
        this.listItemCart = listItemCart;
    }
}
