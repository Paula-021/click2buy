package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.Order;
import com.paula.click2buy.domain.PaymentMethod;
import com.paula.click2buy.domain.User;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {
    @NotNull(message = "idCart cannot be null")
    private Long idCart;
    @NotNull(message = "deliveryAddress cannot be null")
    private AddressOrderRequestDTO deliveryAddress;
    @NotNull(message = "idUser cannot be null")
    private Long idUser;
    @NotNull(message = "paymentMethod cannot be null")
    private PaymentMethod paymentMethod;


    public Long getIdCart() {
        return idCart;
    }

    public void setIdCart(Long idCart) {
        this.idCart = idCart;
    }

    public AddressOrderRequestDTO getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(AddressOrderRequestDTO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Order toEntity(Cart cart, User user) {
        Order order = new Order();

        order.setCart(cart);
        order.setUser(user);
        order.setPaymentMethod(this.paymentMethod);
        order.setDeliveryAddress(this.deliveryAddress.toEntity());

        return order;

    }
}
