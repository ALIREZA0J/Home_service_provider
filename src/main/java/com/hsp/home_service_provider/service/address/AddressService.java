package com.hsp.home_service_provider.service.address;

import com.hsp.home_service_provider.exception.AddressException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.model.Address;
import com.hsp.home_service_provider.model.Customer;
import com.hsp.home_service_provider.repository.address.AddressRepository;
import com.hsp.home_service_provider.service.customer.CustomerService;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final Validation validation;

    public Address register(Address address){
        if (!validation.checkPositiveNumber(address.getPlaque().longValue()))
            throw new AddressException("plaque couldn't be negative.");
        return addressRepository.save(address);
    }

    public List<Address> findAddressesByCustomer(Customer customer){
        List<Address> addressesOfCustomer = addressRepository.findAddressesByCustomer(customer);
        if (addressesOfCustomer.isEmpty())
            throw new NotFoundException("Customer with (id:"+customer.getId()+") has no any addresses.");
        return addressesOfCustomer;
    }

    public Address findById(Long id){
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address with (id:" + id + ") not found."));
    }
}
