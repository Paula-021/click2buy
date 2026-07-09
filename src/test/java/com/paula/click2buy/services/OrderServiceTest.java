package com.paula.click2buy.services;

import com.paula.click2buy.domain.*;
import com.paula.click2buy.endpoints.dtos.OrderResponseDTO;
import com.paula.click2buy.exceptions.StockQuantityNotFoundException;
import com.paula.click2buy.payments.endpoints.dtos.StripeCheckoutResponseDTO;
import com.paula.click2buy.payments.services.CheckoutStripeService;
import com.paula.click2buy.payments.services.CurrencyService;
import com.paula.click2buy.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private CheckoutStripeService checkoutStripeService; // Adicione o mock para o CheckoutStripeService


    @InjectMocks
    private OrderServiceImpl orderService;

    /*@Test
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
    }*/


    @Test
    void shouldAddOrderSuccessfully() {
        // Arrange
        Product product = new Product();
        product.setName("Produto 1");
        product.setStockQuantity(10);
        product.setPrice(100.0);

        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(product);
        itemCart.setQuantity(5);

        Cart cart = new Cart();
        cart.setListItemCart(List.of(itemCart));
        cart.setTotalPriceBrl(500.0);

        ShippingOption shippingOption = new ShippingOption();
        shippingOption.setDeliveryTime(5);
        cart.setShippingSelected(shippingOption);

        Address address = new Address();
        address.setZipcode("12345-678");
        address.setStreet("Street 1");
        address.setCountry("Canada");

        User user = new User();
        user.setId(1L);
        user.setEmail("email@email.com");



        Order order = new Order();
        order.setCart(cart);
        order.setCurrency("BRL");
        order.setDeliveryAddress(address);
        order.setUser(user);
        order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);


        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(checkoutStripeService.checkout(any(Order.class))).thenReturn(new StripeCheckoutResponseDTO());

        // Act
        OrderResponseDTO response = orderService.addOrder(order);

        // Assert
        assertEquals(OrderStatus.AWAITING_PAYMENT, order.getOrderStatus());
        assertEquals(LocalDate.now().plusDays(5), order.getExpectedDeliveryDate());
        assertEquals(500.0, order.getTotalPrice());
        verify(orderRepository).save(order);
        verify(checkoutStripeService).checkout(order);
    }


    @Test
    void shouldConvertCurrencyWhenNotBRL() {
        // Arrange
        Product product = new Product();
        product.setName("Produto 1");
        product.setStockQuantity(10);
        product.setPrice(100.0);

        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(product);
        itemCart.setQuantity(5);

        Cart cart = new Cart();
        cart.setListItemCart(List.of(itemCart));
        cart.setTotalPriceBrl(500.0);

        ShippingOption shippingOption = new ShippingOption();
        shippingOption.setDeliveryTime(5);
        cart.setShippingSelected(shippingOption);

        Address address = new Address();
        address.setZipcode("12345-678");
        address.setStreet("Street 1");
        address.setCountry("Canada");

        User user = new User();
        user.setId(1L);
        user.setEmail("email@email.com");



        Order order = new Order();
        order.setCart(cart);
        order.setCurrency("USD");
        order.setDeliveryAddress(address);
        order.setUser(user);
        order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(currencyService.convert("USD", 500.0)).thenReturn(100.0);
        when(checkoutStripeService.checkout(any(Order.class))).thenReturn(new StripeCheckoutResponseDTO());

        // Act
        OrderResponseDTO response = orderService.addOrder(order);

        // Assert
        assertEquals(100.0, order.getTotalPrice());
        verify(currencyService).convert("USD", 500.0);
        verify(orderRepository).save(order);
        verify(checkoutStripeService).checkout(order);
    }





    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        // Arrange
        Product product = new Product();
        product.setName("Produto 1");
        product.setStockQuantity(2);

        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(product);
        itemCart.setQuantity(5);

        Cart cart = new Cart();
        cart.setListItemCart(List.of(itemCart));

        Order order = new Order();
        order.setCart(cart);

        // Act & Assert
        StockQuantityNotFoundException exception = assertThrows(StockQuantityNotFoundException.class, () -> orderService.addOrder(order));
        assertEquals("Quantity requested for product Produto 1 exceeds the stock quantity.".trim(), exception.getMessage().trim());
        verify(orderRepository, never()).save(any());
    }




    @Test
    void shouldSetPendingShipmentStatusWhenOrderIsPaid() {
        // Deve definir o status do pedido como PENDING_SHIPMENT quando o pedido estiver pago
        // Arrange
        Product product = new Product();
        product.setName("Produto 1");
        product.setStockQuantity(10);

        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(product); // Associa o produto ao item do carrinho
        itemCart.setQuantity(5);

        Cart cart = new Cart();
        cart.setListItemCart(List.of(itemCart));

        Order order = new Order();
        order.setCart(cart);
        order.setPaid(true);
        order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        orderService.markOrderAsPaid(1L);

        // Assert
        assertEquals(OrderStatus.PENDING_SHIPMENT, order.getOrderStatus());
        verify(orderRepository).save(order);
    }


    @Test
    void shouldMarkOrderAsPaid() {
        // Arrange
        Order order = new Order();
        order.setId(1L);
        order.setPaid(false);
        order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        orderService.markOrderAsPaid(1L);

        // Assert
        assertEquals(true, order.isPaid());
        assertEquals(OrderStatus.PENDING_SHIPMENT, order.getOrderStatus());
        verify(orderRepository).findById(1L);
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
