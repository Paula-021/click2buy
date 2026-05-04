package com.paula.click2buy.services;

import com.paula.click2buy.domain.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    Address getAddressById(Long idAddress);

    Address addAddress(Address address);

    void updateAddress(Address address);

    void deleteAddress(Long id);

    List<Address> getAllAddresses();
}
