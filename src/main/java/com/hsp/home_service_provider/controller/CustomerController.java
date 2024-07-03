package com.hsp.home_service_provider.controller;


import com.hsp.home_service_provider.dto.address.AddressResponse;
import com.hsp.home_service_provider.dto.address.AddressSaveRequest;
import com.hsp.home_service_provider.dto.customer.CustomerChangePasswordRequest;
import com.hsp.home_service_provider.dto.customer.CustomerResponse;
import com.hsp.home_service_provider.dto.customer.CustomerSaveRequest;
import com.hsp.home_service_provider.dto.offer.OfferResponse;
import com.hsp.home_service_provider.dto.order.OrderOfCustomerResponse;
import com.hsp.home_service_provider.dto.order.OrderResponse;
import com.hsp.home_service_provider.dto.order.OrderSaveRequest;
import com.hsp.home_service_provider.mapper.AddressMapper;
import com.hsp.home_service_provider.mapper.CustomerMapper;
import com.hsp.home_service_provider.mapper.OfferMapper;
import com.hsp.home_service_provider.mapper.OrderMapper;
import com.hsp.home_service_provider.model.Address;
import com.hsp.home_service_provider.model.Customer;
import com.hsp.home_service_provider.model.Offer;
import com.hsp.home_service_provider.model.Order;
import com.hsp.home_service_provider.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/register-new-order")
    public ResponseEntity<OrderResponse> registerNewOrder(@RequestBody OrderSaveRequest request){
        Order order = OrderMapper.INSTANCE.orderSaveRequestToModel(request);
        Order registerNewOrder = customerService.registerNewOrder(order);
        return new ResponseEntity<>(OrderMapper.INSTANCE.modelToOrderResponse(registerNewOrder),HttpStatus.CREATED);
    }

    @GetMapping("/display-WaitingOffers")
    public ResponseEntity<List<OfferResponse>> displayOffersOfOrderInWaitingStatus(@RequestParam Long orderId){
        List<Offer> offersFind = customerService.displayOffersOfOrderInWaitingStatusAndSortByPriceAndScore(orderId);
        ArrayList<OfferResponse> offerResponses = new ArrayList<>();
        for (Offer offer : offersFind) {
            offerResponses.add(OfferMapper.INSTANCE.offerModelToOfferResponse(offer));
        }
        return new ResponseEntity<>(offerResponses,HttpStatus.OK);
    }

    @PutMapping("/accept-an-offer")
    public ResponseEntity<String> acceptAnOfferForOrder(@RequestParam Long offerId){
        customerService.acceptOfferForOrder(offerId);
        return new ResponseEntity<>("Offer with (id:"+offerId+") is accepted.",HttpStatus.OK);
    }

    @GetMapping("/display-OrdersInWaitingForSpecialistComeToCustomerPlace")
    public ResponseEntity<List<OrderOfCustomerResponse>> displayOrdersInWaitingForSpecialistComeToCustomerPlace
            (@RequestParam Long customerId){
        List<Order> orders = customerService.displayOrdersInWaitingForSpecialistComeToCustomerPlace(customerId);
        ArrayList<OrderOfCustomerResponse> orderOfCustomerResponses = new ArrayList<>();
        for (Order order : orders) {
            orderOfCustomerResponses.add(OrderMapper.INSTANCE.modelToOrderOfCustomerResponse(order));
        }
        return new ResponseEntity<>(orderOfCustomerResponses,HttpStatus.OK);
    }
    @PutMapping("/registration-of-the_start-of-work")
    public ResponseEntity<String> registrationOfTheStartOfWork(@RequestParam Long orderId){
        customerService.registrationOfTheStartOfWork(orderId);
        return new ResponseEntity<>("Order with (id:"+orderId+") Started.",HttpStatus.OK);
    }

    @GetMapping("/display-OrdersInStartedStatus")
    public ResponseEntity<List<OrderOfCustomerResponse>> displayOrdersInStartedStatus(@RequestParam Long customerId){
        List<Order> orders = customerService.displayOrdersStarted(customerId);
        ArrayList<OrderOfCustomerResponse> orderOfCustomerResponses = new ArrayList<>();
        for (Order order : orders) {
            orderOfCustomerResponses.add(OrderMapper.INSTANCE.modelToOrderOfCustomerResponse(order));
        }
        return new ResponseEntity<>(orderOfCustomerResponses,HttpStatus.OK);
    }

    @PutMapping("/end-of-work-registration")
    public ResponseEntity<String> endOfTimeRegistration(@RequestParam Long orderId){
        customerService.endOfWorkRegistration(orderId);
        return new ResponseEntity<>("Order with (id:"+orderId+") Done.",HttpStatus.OK);
    }

}
