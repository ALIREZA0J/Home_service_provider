package com.hsp.home_service_provider.service.customer;

import com.hsp.home_service_provider.exception.CustomerException;
import com.hsp.home_service_provider.exception.DuplicateException;
import com.hsp.home_service_provider.exception.MismatchException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.model.*;
import com.hsp.home_service_provider.repository.customer.CustomerRepository;
import com.hsp.home_service_provider.service.address.AddressService;
import com.hsp.home_service_provider.service.offer.OfferService;
import com.hsp.home_service_provider.service.order.OrderService;
import com.hsp.home_service_provider.service.subservice.SubServiceService;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final AddressService addressService;
    private final OfferService offerService;
    private final SubServiceService subServiceService;
    private final Validation validation;


    public Customer register(Customer customer){
        validation.validate(customer);
        if (customerRepository.findCustomerByGmail(customer.getGmail()).isPresent())
            throw new DuplicateException("A customer with this gmail is already exist.");
        customer.setRegistrationDate(LocalDate.now());
        customer.setCredit(0L);
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer changePassword(String gmail, String password1,String password2){
        Customer customer = findByGmail(gmail);
        if (!password1.equals(password2))
            throw new MismatchException("Password and repeat password do not match");
        customer.setPassword(password2);
        validation.validate(customer);
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

    public Customer findByGmail(String gmail){
        return customerRepository.findCustomerByGmail(gmail)
                .orElseThrow(() -> new NotFoundException("Customer with (gmail:" + gmail + ") not found."));
    }

    public Address registerNewAddress(Address address, String gmail){
        Customer customer = findByGmail(gmail);
        address.setCustomer(customer);
        return addressService.register(address);
    }

    public Order registerNewOrder(Order order){
        SubService subService = subServiceService.findByName(order.getSubService().getName());
        validation.checkProposedPriceNotLessThanSubService(order.getProposedPrice(), subService.getBasePrice());
        if (order.getProposedPrice() == null)
            order.setProposedPrice(subService.getBasePrice());
        order.setCustomer(findByGmail(order.getCustomer().getGmail()));
        order.setSubService(subService);
        order.setAddress(addressService.findById(order.getAddress().getId()));
        return orderService.register(order);
    }

    public List<Offer> displayOffersOfOrderInWaitingStatusAndSortByPriceAndScore(Long orderId){
        Order order = orderService.findById(orderId);
        return offerService.displayOffersOfOrderSortByPriceAndScore(order);
    }
}
