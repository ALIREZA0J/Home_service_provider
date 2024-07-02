//package com.hsp.home_service_provider.service.customer;
//
//import com.github.mfathi91.time.PersianDate;
//import com.hsp.home_service_provider.exception.*;
//import com.hsp.home_service_provider.model.Address;
//import com.hsp.home_service_provider.model.Customer;
//import com.hsp.home_service_provider.model.Order;
//import com.hsp.home_service_provider.model.enums.City;
//import com.hsp.home_service_provider.repository.customer.CustomerRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class CustomerServiceTest {
//
//    @Autowired
//    CustomerService customerService;
//
//    @Autowired
//    CustomerRepository customerRepository;
//
//    @Test
//    @org.junit.jupiter.api.Order(1)
//    void givenMethodCalledRegister_whenEveryThingIsOk_thenReturnProperResult() {
//        Customer customer = Customer.builder()
//                .setFirstName("testCustomer").setLastName("testCustomer")
//                .setGmail("testCustomer@specialistGmail.com").setPassword("TESTcustomer@#123")
//                .build();
//
//        Customer registerCustomer = customerService.register(customer);
//        Assertions.assertTrue(registerCustomer.getId()>0);
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(2)
//    void givenMethodCalledRegister_whenDataIsNotValid_thenThrowException() {
//        Customer customer = Customer.builder()
//                .setFirstName("123").setLastName("123")
//                .setGmail("testCustomer@123").setPassword("test")
//                .build();
//
//        Assertions.assertThrows(NotValidException.class, () -> customerService.register(customer));
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(3)
//    void givenMethodCalledRegister_whenGmailIsDuplicate_thenThrowException() {
//        Customer customer = Customer.builder()
//                .setFirstName("testCustomer").setLastName("testCustomer")
//                .setGmail("testCustomer@specialistGmail.com").setPassword("TESTcustomer@#123")
//                .build();
//
//        Assertions.assertThrows(DuplicateException.class, () -> customerService.register(customer));
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(4)
//    void givenMethodCalledLogIn_whenEveryThingIsOk_thenReturnProperResult() {
//        String specialistGmail = "testCustomer@specialistGmail.com";
//        String password = "TESTcustomer@#123";
//
//        Customer customer = customerService.logIn(specialistGmail, password);
//        Customer customerById = customerRepository.findById(1L).get();
//
//        Assertions.assertEquals(customerById, customer);
//    }
//
//
//    @Test
//    @org.junit.jupiter.api.Order(5)
//    void givenMethodCalledLogIn_whenGmailIsWrong_thenThrowException() {
//        String specialistGmail = "testcustomer@specialistGmail.com";
//        String password = "TESTcustomer@#123";
//
//        Assertions.assertThrows(CustomerException.class, () -> customerService.logIn(specialistGmail, password));
//    }
//
//
//    @Test
//    @org.junit.jupiter.api.Order(6)
//    void givenMethodCalledRegisterNewAddress_whenEveryThingIsOk_thenReturnProperResult() {
//        Address address = Address.builder()
//                .setCity(City.TEHRAN).setStreet("Valiasr")
//                .setAlley("Khadem_Azad").setPlaque(6)
//                .build();
//
//        Address registerNewAddress = customerService.registerNewAddress(address, 1L);
//        Customer customer = customerService.findById(1L);
//
//        assertEquals(1L, (long) registerNewAddress.getCustomer().getId());
//        assertEquals(address.getCustomer(), customer);
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(7)
//    void givenMethodCalledRegisterNewAddress_whenPlagueIsNegative_thenThrowException() {
//        Address address = Address.builder()
//                .setCity(City.TEHRAN).setStreet("Valiasr")
//                .setAlley("Khadem_Azad").setPlaque(-6)
//                .build();
//
//        assertThrows(AddressException.class, () -> customerService.registerNewAddress(address, 1L));
//    }
//
//
//    @Test
//    @org.junit.jupiter.api.Order(8)
//    void givenMethodCalledRegisterNewOrder_whenEveryThingIsOk_thenReturnProperResult() {
//        Order order = Order.builder()
//                .setDescription("We need some on to Test My project register order")
//                .setDateOfWork(PersianDate.of(1403, 4, 20).toGregorian())
//                .setTimeOfWork(LocalTime.of(15, 30))
//                .setProposedPrice(9000000L)
//                .build();
//
//        Order newOrder = customerService.registerNewOrder(order, 1L, "SubServiceName", 1L);
//        Customer customer = customerService.findById(1L);
//        assertNotNull(newOrder.getId());
//        Assertions.assertEquals(newOrder.getCustomer(), customer);
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(9)
//    void givenMethodCalledRegisterNewOrder_whenAddressNotFound_thenReturnProperResult() {
//        Order order = Order.builder()
//                .setDescription("We need some on to Test My project register order")
//                .setDateOfWork(PersianDate.of(1403, 4, 20).toGregorian())
//                .setTimeOfWork(LocalTime.of(15, 30))
//                .setProposedPrice(1000000000L)
//                .build();
//
//        assertThrows(NotFoundException.class, () ->
//                customerService.registerNewOrder(order, 1L, "SubServiceName", 100L));
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(10)
//    void givenMethodCalledRegisterNewOrder_whenProposedPriceLessThanBasePrice_thenThrowException() {
//        Order order = Order.builder()
//                .setDescription("We need some on to Test My project register order")
//                .setDateOfWork(PersianDate.of(1403, 4, 20).toGregorian())
//                .setTimeOfWork(LocalTime.of(15, 30))
//                .setProposedPrice(100L)
//                .build();
//
//        assertThrows(ProposedPriceException.class, () ->
//                customerService.registerNewOrder(order, 1L, "SubServiceName", 1L));
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(11)
//    void givenMethodCalledRegisterNewOrder_whenDateOfWorkBeforeToday_thenThrowException() {
//        Order order = Order.builder()
//                .setDescription("We need some on to Test My project register order")
//                .setDateOfWork(PersianDate.of(1400, 4, 20).toGregorian())
//                .setTimeOfWork(LocalTime.of(15, 30))
//                .setProposedPrice(50000000L)
//                .build();
//
//        assertThrows(OrderException.class, () ->
//                customerService.registerNewOrder(order, 1L, "SubServiceName", 1L));
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(12)
//    void givenMethodCalledRegisterNewOrder_whenDescriptionIsBlank_thenThrowException() {
//        Order order = Order.builder()
//                .setDescription("")
//                .setDateOfWork(PersianDate.of(1403, 4, 20).toGregorian())
//                .setTimeOfWork(LocalTime.of(15, 30))
//                .setProposedPrice(50000000L)
//                .build();
//
//        assertThrows(DescriptionException.class, () ->
//                customerService.registerNewOrder(order, 1L, "SubServiceName", 1L));
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(13)
//    void givenMethodCalledRegisterNewOrder_whenDescriptionConsistJustWithNumber_thenThrowException() {
//        Order order = Order.builder()
//                .setDescription("45342136")
//                .setDateOfWork(PersianDate.of(1403, 4, 20).toGregorian())
//                .setTimeOfWork(LocalTime.of(15, 30))
//                .setProposedPrice(50000000L)
//                .build();
//
//        assertThrows(DescriptionException.class, () ->
//                customerService.registerNewOrder(order, 1L, "SubServiceName", 1L));
//    }
//
//    @Test
//    @org.junit.jupiter.api.Order(14)
//    void givenMethodCalledRegisterNewOrder_whenNoProposedPrice_thenSetSubServiceBasePriceForProposedPrice(){
//        Order order = Order.builder()
//                .setDescription("We need some on to Test My project register order")
//                .setDateOfWork(PersianDate.of(1403, 4, 20).toGregorian())
//                .setTimeOfWork(LocalTime.of(15, 30))
//                .build();
//
//        Order newOrder = customerService.registerNewOrder(order, 1L, "SubService", 1L);
//        Customer customer = customerService.findById(1L);
//        assertNotNull(newOrder.getId());
//        Assertions.assertEquals(newOrder.getCustomer(), customer);
//    }
//    @Test
//    @org.junit.jupiter.api.Order(15)
//    void givenMethodCalledRegisterNewOrder_whenNoProposedPrice2_thenSetSubServiceBasePriceForProposedPrice(){
//        Order order = Order.builder()
//                .setDescription("We need some on to Test My project register order")
//                .setDateOfWork(PersianDate.of(1403, 4, 20).toGregorian())
//                .setTimeOfWork(LocalTime.of(15, 30))
//                .build();
//
//        Order newOrder = customerService.registerNewOrder(order, 1L, "SubServiceName", 1L);
//        Customer customer = customerService.findById(1L);
//        assertNotNull(newOrder.getId());
//        Assertions.assertEquals(newOrder.getCustomer(), customer);
//    }
//
//
//}