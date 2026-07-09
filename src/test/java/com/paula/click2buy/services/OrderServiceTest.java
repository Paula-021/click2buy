package com.paula.click2buy.services;

import com.paula.click2buy.domain.*;
import com.paula.click2buy.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldSetPendingShipmentStatusWhenOrderIsPaid() {
        // Deve definir o status do pedido como PENDING_SHIPMENT quando o pedido estiver pago

        // Arrange
        Product product = new Product();
        product.setPrice(100.0);

        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(product);
        itemCart.setQuantity(2);

        ArrayList<ItemCart> itemCarts = new ArrayList<>();
        itemCarts.add(itemCart);

        Cart cart = new Cart();
        cart.setListItemCart(itemCarts);

        Order order = new Order();
        order.setCart(cart);
        order.setPaid(true);

        //quando o método save do orderRepository for chamado com qualquer objeto do tipo Order, ele deve retornar o mesmo objeto Order que foi passado como parâmetro.
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //Act
        orderService.addOrder(order);

        //Assert
        assertEquals(OrderStatus.PENDING_SHIPMENT, order.getOrderStatus());
        assertEquals(LocalDate.now(), order.getOrderDate());
        verify(orderRepository).save(order);

    }
    @Test
    void shouldSetAwaitingPaymentStatusWhenOrderIsNotPaid() {
        // Deve definir o status do pedido como AWAITING_PAYMENT quando o pedido não estiver pago

        // Arrange
        Product product = new Product();
        product.setPrice(50.0);

        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(product);
        itemCart.setQuantity(1);

        ArrayList<ItemCart> itemCarts = new ArrayList<>();
        itemCarts.add(itemCart);

        Cart cart = new Cart();
        cart.setListItemCart(itemCarts);

        Order order = new Order();
        order.setCart(cart);
        order.setPaid(false);

        //quando o método save do orderRepository for chamado com qualquer objeto do tipo Order, ele deve retornar o mesmo objeto Order que foi passado como parâmetro.
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //Act
        orderService.addOrder(order);

        //Assert
        assertEquals(OrderStatus.AWAITING_PAYMENT, order.getOrderStatus());
        assertEquals(LocalDate.now(), order.getOrderDate());
        verify(orderRepository).save(order);
    }

    @Test
    void shouldReturnAllOrders() {
        // Arrange
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        // Act
        List<Order> orders = orderService.getAllOrders();

        // Assert
        assertEquals(2, orders.size());
        assertEquals(order1, orders.get(0));
        assertEquals(order2, orders.get(1));
        verify(orderRepository).findAll();

    }

}
