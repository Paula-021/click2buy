package com.paula.click2buy.payments.services;

import com.paula.click2buy.domain.Order;
import com.paula.click2buy.payments.endpoints.dtos.StripeCheckoutResponseDTO;
import com.stripe.Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CheckoutStripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    public StripeCheckoutResponseDTO checkout(Order order) {
        Stripe.apiKey = secretKey;

        // nome vai ser gerado (Order #id)

        // amount vai ser o valor total do carrinho (é total dos produtos + frete), multiplicado por 100 para converter para a menor unidade da moeda (ex: R$10,00 -> 1000 centavos)
        // o amount já vai vim o valor correto conforme a moeda que o usuário escolheu pagar
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName("Order #"+order.getId())
                .build();


        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(order.getCurrency())
                .setUnitAmountDecimal(BigDecimal.valueOf(order.getTotalPrice()).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))) // valor total do carrinho + frete, já convertido para a moeda correta
                .setProductData(productData) //Unit Amount Decimal => quantidade (de dinheiro) em unidade decimal
                .build();
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(priceData)
                .setQuantity(1L)
                .build();
        SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(lineItem)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .build();

        try {
            Session session = Session.create(params);

            StripeCheckoutResponseDTO responseDTO = new StripeCheckoutResponseDTO();
            responseDTO.setStatus("success");
            responseDTO.setMessage("Checkout session created successfully");
            responseDTO.setSessionId(session.getId());
            responseDTO.setSessionUrl(session.getUrl());

            return responseDTO;

        } catch (StripeException e) {
            StripeCheckoutResponseDTO responseDTO = new StripeCheckoutResponseDTO();
            responseDTO.setStatus("failure");
            responseDTO.setMessage("Error creating checkout session: " + e.getMessage());
            return responseDTO;
        }
    }
}
