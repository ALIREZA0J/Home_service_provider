package com.hsp.home_service_provider.controller;


import com.hsp.home_service_provider.dto.address.AddressResponse;
import com.hsp.home_service_provider.dto.address.AddressSaveRequest;
import com.hsp.home_service_provider.dto.customer.CustomerChangePasswordRequest;
import com.hsp.home_service_provider.dto.customer.CustomerResponse;
import com.hsp.home_service_provider.dto.customer.CustomerSaveRequest;
import com.hsp.home_service_provider.mapper.AddressMapper;
import com.hsp.home_service_provider.mapper.CustomerMapper;
import com.hsp.home_service_provider.model.Address;
import com.hsp.home_service_provider.model.Customer;
import com.hsp.home_service_provider.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/change-password")
    public ResponseEntity<CustomerResponse> changePassword(@RequestBody CustomerChangePasswordRequest request){
        Customer customer = customerService.changePassword(request.gmail(), request.password1(), request.password2());
        return new ResponseEntity<>
                (CustomerMapper.INSTANCE.customerModelToCustomerResponse(customer), HttpStatus.OK);
    }

    @PostMapping("/register-new-address")
    public ResponseEntity<AddressResponse> registerNewAddress(@RequestBody AddressSaveRequest request){
        Address address = AddressMapper.INSTANCE.addressSaveRequestToModel(request);
        Address registerNewAddress = customerService.registerNewAddress(address, request.customer().gmail());
        return new ResponseEntity<>
                (AddressMapper.INSTANCE.addressModelToAddressResponse(registerNewAddress), HttpStatus.CREATED);
    }
}
