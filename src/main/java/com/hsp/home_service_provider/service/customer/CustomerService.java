package com.hsp.home_service_provider.service.customer;

import com.hsp.home_service_provider.exception.CustomerException;
import com.hsp.home_service_provider.exception.DuplicateException;
import com.hsp.home_service_provider.exception.NotValidException;
import com.hsp.home_service_provider.model.Customer;
import com.hsp.home_service_provider.repository.customer.CustomerRepository;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
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

}
