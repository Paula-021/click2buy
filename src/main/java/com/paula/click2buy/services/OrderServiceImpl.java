package com.paula.click2buy.services;

import com.paula.click2buy.domain.Order;
import com.paula.click2buy.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Order order) {

        orderRepository.save(order);

    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
