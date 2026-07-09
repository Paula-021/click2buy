package com.paula.click2buy.endpoints;

import com.paula.click2buy.domain.Address;
import com.paula.click2buy.endpoints.dtos.AddressRequestDTO;
import com.paula.click2buy.endpoints.dtos.AddressResponseDTO;
import com.paula.click2buy.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressRequestDTO addressRequestDTO) {
        Address address = addressService.addAddress(addressRequestDTO.toEntity());
        return ResponseEntity.status(201).body(address);//201 CREATED
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressRequestDTO addressRequestDTO, @PathVariable Long id) {
        Address address = addressRequestDTO.toEntity();
        address.setId(id);
        addressService.updateAddress(address);
        return ResponseEntity.ok().body("Address updated successfully!");//200 OK

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().body("Address deleted successfully!");//200 OK
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO(address);
        return ResponseEntity.ok().body(addressResponseDTO);//200 OK
    }
    @GetMapping
    public ResponseEntity<?> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
            List<AddressResponseDTO> addressResponseDTOs = addresses.stream().map(AddressResponseDTO::new).toList();
            return ResponseEntity.ok().body(addressResponseDTOs);//200 OK
    }
}
