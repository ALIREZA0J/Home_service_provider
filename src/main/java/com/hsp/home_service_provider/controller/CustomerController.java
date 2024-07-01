package com.hsp.home_service_provider.controller;


import com.hsp.home_service_provider.dto.customer.CustomerResponse;
import com.hsp.home_service_provider.dto.customer.CustomerSaveRequest;
import com.hsp.home_service_provider.mapper.CustomerMapper;
import com.hsp.home_service_provider.model.Customer;
import com.hsp.home_service_provider.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@RequestBody CustomerSaveRequest request){
        Customer customer = CustomerMapper.INSTANCE.customerSaveRequestToModel(request);
        Customer registerCustomer = customerService.register(customer);
        return new ResponseEntity<>
                (CustomerMapper.INSTANCE.customerModelToCustomerResponse(registerCustomer), HttpStatus.CREATED);
    }
}
