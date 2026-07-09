package com.paula.click2buy.services;


import com.paula.click2buy.domain.Order;
import com.paula.click2buy.endpoints.dtos.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderResponseDTO addOrder(Order order);

    List<Order> getAllOrders();

    void markOrderAsPaid(Long id);

    void updateOrderStatus(Long id);

    void updateOrderAddress(Long id, Long idAddress);

    void updateOrderStatusCancel(Long id);
}
