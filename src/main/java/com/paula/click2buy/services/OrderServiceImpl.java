package com.paula.click2buy.services;

import com.paula.click2buy.domain.*;
import com.paula.click2buy.repositories.OrderRepository;
import com.paula.click2buy.shipment.dtos.ShipmentRequestDTO;
import com.paula.click2buy.shipment.services.MelhorEnvioShipmentCalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MelhorEnvioShipmentCalculateService melhorEnvioShipmentCalculateService;
    @Autowired
    private AddressService addressService;

    @Override
    public void addOrder(Order order) {
        //chegou com deliveryAddress, paymentMethod, cart, user

        //expectedDate
        Integer deliveryTime = order.getCart().getShippingSelected().getDeliveryTime();
        LocalDate expectedDate = LocalDate.now().plusDays(deliveryTime);
        order.setExpectedDeliveryDate(expectedDate);

        //orderDate
        order.setOrderDate(LocalDate.now());

        //paid
        order.setPaid(false);

        //orderStatus
        order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);

        //trackingNumber - gerar um código aleatório
        //não vai ter tranking number assim que o pedido for criado, só depois que o pedido for pago e enviado que o tracking number vai ser gerado, então o tracking number só vai ser setado quando o status do pedido for "enviado"


        orderRepository.save(order);

    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
    }

    @Override
    public void markOrderAsPaid(Long id) {
       Order order = getOrderById(id);
       order.setPaid(true);
       order.setOrderStatus(OrderStatus.PENDING_SHIPMENT);
       orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(Long id) {
        Order order = getOrderById(id);
        if(order.getOrderStatus() == OrderStatus.PENDING_SHIPMENT) {
            order.setOrderStatus(OrderStatus.SHIPPED);
            //gerar tracking number
            String trackingNumber = "TRK" + order.getId() + System.currentTimeMillis();
            order.setTrackingNumber(trackingNumber);
        } else if(order.getOrderStatus() == OrderStatus.SHIPPED) {
            order.setOrderStatus(OrderStatus.DELIVERED);
            order.setOrderDate(LocalDate.now());
        }
        orderRepository.save(order);
    }

    @Override
    public void updateOrderAddress(Long id, Long idAddress) {

        //só é possivel alterar o endereço do pedido para outro endereço que o usuário tiver na conta

        //mostrar os endereços do usuário para o usuário escolher qual endereço ele quer colocar no pedido, ou seja, o usuário não pode digitar um endereço novo, ele tem que escolher um endereço que já esteja cadastrado na conta dele. para isso, o endpoint de updateOrderAddress poderia receber o id do endereço que o usuário quer colocar no pedido e não o endereço em si. então, nesse método, a gente buscaria o endereço pelo id e setaria esse endereço no pedido.

     Order order = getOrderById(id);

     Address address = addressService.getAddressById(idAddress);


     User user = order.getUser();
     if(!user.getAddresses().contains(address)) {
            throw new RuntimeException("O endereço selecionado não pertence ao usuário do pedido.");
     }

     if(order.getOrderStatus() == OrderStatus.AWAITING_PAYMENT || order.getOrderStatus() == OrderStatus.PENDING_SHIPMENT) {
         order.setDeliveryAddress(address);
         orderRepository.save(order);
     } else {
         throw new RuntimeException("Não é possível alterar o endereço para pedidos que já foram enviados ou entregues.");
     }

    }

    @Override
    public void updateOrderStatusCancel(Long id) {
        Order order = getOrderById(id);
        if(order.getOrderStatus() == OrderStatus.AWAITING_PAYMENT || order.getOrderStatus() == OrderStatus.PENDING_SHIPMENT) {
            order.setOrderStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Não é possível cancelar pedidos que já foram enviados ou entregues.");
        }

    }
}
