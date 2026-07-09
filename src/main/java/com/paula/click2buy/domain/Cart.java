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

    private Double totalPrice;

    @OneToOne
    private ShippingOption shippingSelected;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ShippingOption getShippingSelected() {
        return shippingSelected;
    }

    public void setShippingSelected(ShippingOption shippingSelected) {
        this.shippingSelected = shippingSelected;
    }

    private Double totalPriceBrl;

    @OneToOne
    private ShippingOption shippingSelected;


    public Double getTotalPriceBrl() {
        return totalPriceBrl;
    }

    public void setTotalPriceBrl(Double totalPrice) {
        this.totalPriceBrl = totalPrice;
    }

    public ShippingOption getShippingSelected() {
        return shippingSelected;
    }

    public void setShippingSelected(ShippingOption shippingSelected) {
        this.shippingSelected = shippingSelected;
    }

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
