package com.paula.click2buy.services;

import com.paula.click2buy.domain.Address;
import com.paula.click2buy.exceptions.AddressNotFoundException;
import com.paula.click2buy.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Override
    public Address getAddressById(Long idAddress) {
        return addressRepository.findById(idAddress).orElseThrow(() -> new AddressNotFoundException());
    }

    @Override
    public Address addAddress(Address address) {

        return addressRepository.save(address);
    }

    @Override
    public void updateAddress(Address address) {

            addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        getAddressById(id);
        addressRepository.deleteById(id);

    }

    @Override
    public List<Address> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses;
    }
}
