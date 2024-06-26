package com.hsp.home_service_provider.service.order;

import com.github.mfathi91.time.PersianDate;
import com.hsp.home_service_provider.exception.DescriptionException;
import com.hsp.home_service_provider.exception.OrderException;
import com.hsp.home_service_provider.model.Address;
import com.hsp.home_service_provider.model.Customer;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.model.enums.OrderStatus;
import com.hsp.home_service_provider.repository.order.OrderRepository;
import com.hsp.home_service_provider.service.address.AddressService;
import com.hsp.home_service_provider.service.customer.CustomerService;
import com.hsp.home_service_provider.service.subservice.SubServiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SubServiceService subServiceService;
    @Autowired
    AddressService addressService;


    @Test
    @Order(1)
    void givenMethodCalledRegister_whenEveryThingIsOk_thenReturnProperResult(){
        Long customerId = 1L;
        String subServiceName = "SubServiceName";
        Long addressId = 1L;

        Customer customer = customerService.findById(customerId);
        SubService subService = subServiceService.findByName(subServiceName);
        Address address = addressService.findById(addressId);

        com.hsp.home_service_provider.model.Order order = orderService.register(com.hsp.home_service_provider.model.Order.builder()
                .setAddress(address).setCustomer(customer).setSubService(subService)
                .setDescription("test order in OrderTest")
                .setProposedPrice(9000000L)
                .setDateOfWork(PersianDate.of(1403, 5, 5).toGregorian())
                .setTimeOfWork(LocalTime.of(5, 30))
                .build());

        Assertions.assertEquals(order.getOrderStatus(), OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST);
    }
    @Test
    @Order(2)
    void givenMethodCalledRegister_whenDateOfWorkIsPassed_thenThrowException(){
        Long customerId = 1L;
        String subServiceName = "SubServiceName";
        Long addressId = 1L;

        Customer customer = customerService.findById(customerId);
        SubService subService = subServiceService.findByName(subServiceName);
        Address address = addressService.findById(addressId);

        assertThrows(OrderException.class,() -> orderService.register(com.hsp.home_service_provider.model.Order.builder()
                .setAddress(address).setCustomer(customer).setSubService(subService)
                .setDescription("test order in OrderTest")
                .setProposedPrice(5000000L)
                .setDateOfWork(PersianDate.of(1403, 4, 1).toGregorian())
                .setTimeOfWork(LocalTime.of(5, 30))
                .build()));
    }
    @Test
    @Order(3)
    void givenMethodCalledRegister_whenNoDescription_thenThrowException(){
        Long customerId = 1L;
        String subServiceName = "SubServiceName";
        Long addressId = 1L;

        Customer customer = customerService.findById(customerId);
        SubService subService = subServiceService.findByName(subServiceName);
        Address address = addressService.findById(addressId);

        assertThrows(DescriptionException.class,() -> orderService.register(com.hsp.home_service_provider.model.Order.builder()
                .setAddress(address).setCustomer(customer).setSubService(subService)
                .setDescription("")
                .setProposedPrice(5000000L)
                .setDateOfWork(PersianDate.of(1403, 4, 31).toGregorian())
                .setTimeOfWork(LocalTime.of(5, 30))
                .build()));
    }
    @Test
    @Order(4)
    void givenMethodCalledRegister_whenDescriptionIsConsistWithNumber_thenThrowException(){
        Long customerId = 1L;
        String subServiceName = "SubServiceName";
        Long addressId = 1L;

        Customer customer = customerService.findById(customerId);
        SubService subService = subServiceService.findByName(subServiceName);
        Address address = addressService.findById(addressId);

        assertThrows(DescriptionException.class,() -> orderService.register(com.hsp.home_service_provider.model.Order.builder()
                .setAddress(address).setCustomer(customer).setSubService(subService)
                .setDescription("456454136")
                .setProposedPrice(5000000L)
                .setDateOfWork(PersianDate.of(1403, 4, 31).toGregorian())
                .setTimeOfWork(LocalTime.of(5, 30))
                .build()));
    }

    @Test
    @Order(5)
    void givenMethodCalledChangeStatusOfOrderToWaitingForSpecialistSelection_whenEveryThingIsOk_thenReturnProperResult(){
        com.hsp.home_service_provider.model.Order orderBefore = orderService.findById(3L);
        orderService.changeStatusOfOrderToWaitingForSpecialistSelection(3L);
        com.hsp.home_service_provider.model.Order orderAfter = orderService.findById(3L);

        assertNotEquals(orderBefore.getOrderStatus(),orderAfter.getOrderStatus());
    }

    @Test
    @Order(6)
    void givenMethodCalledChangeStatusOfOrderToWaitingForSpecialistGoToCustomerPlace_whenEveryThingIsOk_thenReturnProperResult(){
        com.hsp.home_service_provider.model.Order orderBefore = orderService.findById(3L);
        orderService.changeStatusOfOrderToWaitingForSpecialistGoToCustomerPlace(3L);
        com.hsp.home_service_provider.model.Order orderAfter = orderService.findById(3L);

        assertNotEquals(orderBefore.getOrderStatus(),orderAfter.getOrderStatus());
    }

    @Test
    @Order(7)
    void givenMethodCalledChangeStatusOfOrderToStart_whenEverThingIsOk_thenReturnProperResult(){
        com.hsp.home_service_provider.model.Order orderBefore = orderService.findById(3L);
        orderService.changeStatusOfOrderToStart(3L);
        com.hsp.home_service_provider.model.Order orderAfter = orderService.findById(3L);

        assertNotEquals(orderBefore.getOrderStatus(),orderAfter.getOrderStatus());
    }
    @Test
    @Order(8)
    void givenMethodCalledChangeStatusOfOrderToDone_whenEverThingIsOk_thenReturnProperResult(){
        com.hsp.home_service_provider.model.Order orderBefore = orderService.findById(3L);
        orderService.changeStatusOfOrderToDone(3L);
        com.hsp.home_service_provider.model.Order orderAfter = orderService.findById(3L);

        assertNotEquals(orderBefore.getOrderStatus(),orderAfter.getOrderStatus());
    }

}