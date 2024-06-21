package com.hsp.home_service_provider.service.customer;

import com.hsp.home_service_provider.exception.*;
import com.hsp.home_service_provider.model.Address;
import com.hsp.home_service_provider.model.Customer;
import com.hsp.home_service_provider.model.Order;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.repository.customer.CustomerRepository;
import com.hsp.home_service_provider.service.address.AddressService;
import com.hsp.home_service_provider.service.order.OrderService;
import com.hsp.home_service_provider.service.subservice.SubServiceService;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final AddressService addressService;
    private final SubServiceService subServiceService;
    private final Validation validation;


    public Customer register(Customer customer){
        if (!validation.validate(customer))
            throw new NotValidException("Your data is not valid.");
        if (customerRepository.findCustomerByGmail(customer.getGmail()).isPresent())
            throw new DuplicateException("A customer with this gmail is already exist.");
        customer.setRegistrationDate(LocalDate.now());
        customer.setCredit(0L);
        return customerRepository.save(customer);
    }

    public Customer logIn(String gmail , String password){
        return customerRepository.findCustomerByGmailAndPassword(gmail, password)
                .orElseThrow(() -> new CustomerException("Gmail or password is wrong."));
    }

    public Customer findById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer with (id:" + id + ") not found."));
    }

    public Address registerNewAddress(Address address, Long customerId){
        Customer customer = findById(customerId);
        address.setCustomer(customer);
        return addressService.register(address);
    }
    public Order registerNewOrder(Order order,Long customerId,String subServiceName,Long addressId){
        Customer customer = findById(customerId);
        SubService subService = subServiceService.findByName(subServiceName);
        Address address = addressService.findById(addressId);
        if (!address.getCustomer().equals(customer))
            throw new MismatchException("The owner of the address is not the same as the owner of the order");
        validation.checkProposedPriceNotLessThanSubService(order.getProposedPrice(), subService.getBasePrice());
        if (order.getProposedPrice() == null)
            order.setProposedPrice(subService.getBasePrice());
        order.setCustomer(customer);
        order.setSubService(subService);
        order.setAddress(address);
        return orderService.register(order);
    }
}
