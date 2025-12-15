package com.paula.click2buy.services;

import com.paula.click2buy.domain.Order;
import com.paula.click2buy.domain.OrderStatus;
import com.paula.click2buy.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Order order) {

        //calcular o preço total do pedido
        double totalPrice = order.getCart().getListItemCart().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        //expectedDate
        //fazer a consulta na API de frete para calcular a data de entrega

        //orderDate
        order.setOrderDate(LocalDate.now());

        //orderStatus
        if(order.isPaid()){
            order.setOrderStatus(OrderStatus.PENDING_SHIPMENT);
        } else {
            order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
        }

        //trackingNumber - gerar um código aleatório

        //paid -> receber da API de pagamento

        orderRepository.save(order);

    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
