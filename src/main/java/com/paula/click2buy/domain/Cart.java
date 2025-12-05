package com.paula.click2buy.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCart> listItemCart;

    public Cart(){
        listItemCart = new ArrayList<>();
    }

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
