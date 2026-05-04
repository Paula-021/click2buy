package com.paula.click2buy.shipment.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paula.click2buy.domain.Product;
import com.paula.click2buy.services.ProductService;
import com.paula.click2buy.shipment.dtos.MelhorEnvioProductDTO;
import com.paula.click2buy.shipment.dtos.ShipmentRequestDTO;
import com.paula.click2buy.shipment.dtos.ShipmentResponseDTO;
import com.paula.click2buy.shipment.utils.MelhorEnvioProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MelhorEnvioShipmentCalculateService {

    @Autowired
    private MelhorEnvioTokenService melhorEnvioTokenService;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MelhorEnvioProperties props;

    @Autowired
    private ProductService productService;


    public List<ShipmentResponseDTO> calculateShipment(ShipmentRequestDTO shipmentRequestDTO) throws JsonProcessingException {

        String token = melhorEnvioTokenService.getAccessToken();

        // buscar produto pelo id e setar o objeto completo no shipmentRequestDTO

        // montar a lista de produtos no formato esperado pela API do Melhor Envio (JSON) => vamos montar uma lista de Map<String, Object>

        List<Map<String, Object>> products = new ArrayList<>();

        for (MelhorEnvioProductDTO productDTO : shipmentRequestDTO.getProducts()){

            Product product = productService.getProductById(productDTO.getProduct().getId());
            productDTO.setProduct(product);

            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", productDTO.getProduct().getId());
            productMap.put("width", productDTO.getProduct().getWidth());
            productMap.put("height", productDTO.getProduct().getHeight());
            productMap.put("length", productDTO.getProduct().getLength());
            productMap.put("weight", productDTO.getProduct().getWeight());
            productMap.put("quantity", productDTO.getQuantity());

            products.add(productMap);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Click2Buy (anapaula.ssouza021@gmail.com)");

        Map<String, Object> body = Map.of(
                "from", Map.of(
                        "postal_code", shipmentRequestDTO.getSender()
                ),
                "to", Map.of(
                        "postal_code", shipmentRequestDTO.getRecipient()
                ),

                "services", "1,2",

                "products", products,

                "options", Map.of(
                        "receipt", false,
                        "own_hand", false,
                        "collect", false
                )
        );


        System.out.println(new ObjectMapper().writeValueAsString(body));

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<List<ShipmentResponseDTO>> response =
                restTemplate.exchange(props.getBaseUrl() + "/api/v2/me/shipment/calculate", HttpMethod.POST, request, new ParameterizedTypeReference<List<ShipmentResponseDTO>>() {});


        return response.getBody();

    }
}
