package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.*;

import java.time.LocalDate;

public class OrderResponseDTO {

    private Double totalPrice;
    private AddressResponseDTO deliveryAddress;
    private LocalDate expectedDeliveryDate;
    private LocalDate deliveryDate;
    private LocalDate orderDate;
    private UserResponseDTO user;
    private PaymentMethod paymentMethod;
    private CartResponseDTO cart;
    private OrderStatus orderStatus;

    public OrderResponseDTO(Order order) {
        this.totalPrice = order.getTotalPrice();
        this.deliveryAddress = new AddressResponseDTO(order.getDeliveryAddress());
        this.expectedDeliveryDate = order.getExpectedDeliveryDate();
        this.deliveryDate = order.getDeliveryDate();
        this.orderDate = order.getOrderDate();
        this.user = new UserResponseDTO(order.getUser());
        this.paymentMethod = order.getPaymentMethod();
        this.cart = new CartResponseDTO(order.getCart());
        this.orderStatus = order.getOrderStatus();
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public AddressResponseDTO getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(AddressResponseDTO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CartResponseDTO getCart() {
        return cart;
    }

    public void setCart(CartResponseDTO cart) {
        this.cart = cart;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
