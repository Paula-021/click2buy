package com.paula.click2buy.services;


import com.paula.click2buy.domain.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    void addOrder(Order order);

    List<Order> getAllOrders();

    void markOrderAsPaid(Long id);

    void updateOrderStatus(Long id);

    void updateOrderAddress(Long id, Long idAddress);

    void updateOrderStatusCancel(Long id);
}
