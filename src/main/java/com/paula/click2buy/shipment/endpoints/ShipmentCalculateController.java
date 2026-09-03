package com.paula.click2buy.shipment.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paula.click2buy.shipment.dtos.ShipmentRequestDTO;
import com.paula.click2buy.shipment.dtos.ShipmentResponseDTO;
import com.paula.click2buy.shipment.services.SuperFreteShipmentCalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shipment/calculate")
public class ShipmentCalculateController {

    @Autowired
    private SuperFreteShipmentCalculateService superFreteShipmentCalculateService;

    @GetMapping
    public ResponseEntity<?> calculate(@RequestBody ShipmentRequestDTO shipmentRequestDTO) throws JsonProcessingException {
        List<ShipmentResponseDTO> shippingResponse = superFreteShipmentCalculateService.calculateShipment(shipmentRequestDTO);
         return ResponseEntity.ok(shippingResponse);
    }


    //testar o refresh token
}
