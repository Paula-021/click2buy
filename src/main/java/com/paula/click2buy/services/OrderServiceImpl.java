package com.paula.click2buy.services;

import com.paula.click2buy.domain.*;
import com.paula.click2buy.endpoints.dtos.OrderResponseDTO;
import com.paula.click2buy.exceptions.StockQuantityNotFoundException;
import com.paula.click2buy.payments.endpoints.dtos.StripeCheckoutResponseDTO;
import com.paula.click2buy.payments.services.CheckoutStripeService;
import com.paula.click2buy.payments.services.CurrencyService;
import com.paula.click2buy.repositories.OrderRepository;
import com.paula.click2buy.shipment.services.SuperFreteShipmentCalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SuperFreteShipmentCalculateService superFreteShipmentCalculateService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CheckoutStripeService checkoutStripeService;

    @Autowired
    private CurrencyService currencyService;

    @Override
    public OrderResponseDTO addOrder(Order order) {
        Cart cart = order.getCart();
        //verificar se os items do carrinho tem estoque
        cart.getListItemCart().forEach(item -> {
            if(item.getQuantity() > item.getProduct().getStockQuantity()) {
                item.setHasStock(false);
                throw new StockQuantityNotFoundException(" Quantity requested for product " +
                        item.getProduct().getName() + " exceeds the stock quantity.");
            }else{
                item.setHasStock(true);
            }
        });

        //salvar pedido
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

        if(!order.getCurrency().equals("BRL")){
            Double totalPriceConverted = currencyService.convert(order.getCurrency(), order.getCart().getTotalPriceBrl() ); //paramos aqui
            //converter  o valor total do pedido para a moeda que o usuário escolheu para que o link de oagamento seja gerado com o valor correto, para isso, a gente pode usar a API de conversão de moedas do Melhor Envio, que é gratuita e fácil de usar. A gente faria uma requisição para a API passando a moeda de origem (BRL) e a moeda de destino (a moeda escolhida pelo usuário) e o valor total do pedido em BRL, e a API retornaria o valor convertido na moeda de destino. Aí a gente setaria esse valor convertido no pedido antes de criar a sessão de pagamento no Stripe.
            //ou a API do bacen ou alguma outra API
            order.setTotalPrice(totalPriceConverted);
            System.out.println("o valor convertido foi salvo no pedido: " + totalPriceConverted);
        }else{
            order.setTotalPrice(order.getCart().getTotalPriceBrl());
        }




        Order orderSaved = orderRepository.save(order);


        // criar a sessao de pagamento no Stripe
        StripeCheckoutResponseDTO stripeCheckoutResponseDTO = checkoutStripeService.checkout(order);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(orderSaved);
        orderResponseDTO.setStripeCheckoutResponseDTO(stripeCheckoutResponseDTO);

        return orderResponseDTO;

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
