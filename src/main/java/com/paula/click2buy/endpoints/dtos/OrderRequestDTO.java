package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.*;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {
    @NotNull(message = "idCart cannot be null")
    private Long idCart;
    @NotNull(message = "deliveryAddress cannot be null")
    private Long idAddress;
    @NotNull(message = "idUser cannot be null")
    private Long idUser;
    @NotNull(message = "paymentMethod cannot be null")
    private String paymentMethod;

    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getIdCart() {
        return idCart;
    }

    public void setIdCart(Long idCart) {
        this.idCart = idCart;
    }

    public Long getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Long idAddress) {
        this.idAddress = idAddress;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Order toEntity(Cart cart, User user, Address address) {
        Order order = new Order();
        order.setCurrency(this.currency);
        order.setCart(cart);
        order.setUser(user);
        order.setPaymentMethod(PaymentMethod.valueOf(this.paymentMethod));
        order.setDeliveryAddress(address);

        return order;

    }
}
