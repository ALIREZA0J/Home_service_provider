//package com.hsp.home_service_provider.service.admin;
//
//import com.hsp.home_service_provider.exception.*;
//import com.hsp.home_service_provider.model.Admin;
//import com.hsp.home_service_provider.model.MainService;
//import com.hsp.home_service_provider.model.SubService;
//import com.hsp.home_service_provider.repository.admin.AdminRepository;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class AdminServiceTest {
//
//    @Autowired
//    AdminService adminService;
//
//    @Autowired
//    AdminRepository adminRepository;
//
//
//    @Test
//    @Order(1)
//    void givenMethodCalledLogIn_whenDataIsRight_thenReturnProperResult() {
//        String specialistGmail = "test@specialistGmail.com";
//        String password = "@TestAdmin123";
//
//        Admin adminS = adminService.logIn(specialistGmail, password);
//        Admin adminById = adminRepository.findById(1L).get();
//
//        Assertions.assertEquals(adminS, adminById);
//    }
//
//
//
//    @Test
//    @Order(2)
//    void givenMethodCalledRegisterMainService_whenEveryThingIsOk_thenReturnProperResult() {
//        String serviceName = "mainServiceTest";
//
//        MainService mainService = MainService.builder().setServiceName(serviceName).build();
//
//        MainService registerMainService = adminService.registerMainService(mainService);
//
//        Assertions.assertEquals(serviceName, registerMainService.getServiceName());
//    }
//
//    @Test
//    @Order(3)
//    void givenMethodCalledRegisterMainService_whenTheServiceNameIsDuplicate_thenThrowDuplicateException() {
//        String serviceName = "mainServiceTest";
//
//        MainService mainService = MainService.builder().setServiceName(serviceName).build();
//
//        Assertions.assertThrows(DuplicateException.class, () -> adminService.registerMainService(mainService));
//    }
//
//
//    @Test
//    @Order(4)
//    void giveMethodCalledRegisterSubService_whenEveryThingIsOk_thenReturnProperResult() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceName").setDescription("SubServiceDescription")
//                .setBasePrice(500000L).build();
//
//        SubService registerSubService = adminService.registerSubService(subService, "mainServiceTest");
//
//        Assertions.assertEquals(1L, registerSubService.getMainService().getId());
//    }
//
//    @Test
//    @Order(5)
//    void giveMethodCalledRegisterSubService_whenNameOfSubServiceIsDuplicate_thenThrowException() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceName").setDescription("SubServiceDescription")
//                .setBasePrice(500000L).build();
//
//        Assertions.assertThrows(DuplicateException.class,
//                () -> adminService.registerSubService(subService, "mainServiceTest"));
//    }
//
//    @Test
//    @Order(6)
//    void givenMethodCalledRegisterSubService_whenMainServiceDoesNotExist_thenThrowException() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceNamE").setDescription("SubServiceDescription")
//                .setBasePrice(500000L).build();
//
//        Assertions.assertThrows(NotFoundException.class,
//                () -> adminService.registerSubService(subService, "mainServiceDoesNotExist"));
//    }
//
//    @Test
//    @Order(7)
//    void givenMethodCalledRegisterSubService_whenBasePriceIsLessThanThe300_000_thenThrowException() {
//        SubService subService = SubService.builder()
//                .setName("dsasdasd").setDescription("asdsadsad")
//                .setBasePrice(2000L).build();
//
//        Assertions.assertThrows(SubServiceException.class,
//                () -> adminService.registerSubService(subService, "mainServiceTest"));
//    }
//
//    @Test
//    @Order(8)
//    void givenMethodCalledUpdateSubService_whenChangeDescriptionAndBasePrice_thenReturnProperResult() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceName")
//                .setDescription("UpdateSubServiceDescription....")
//                .setBasePrice(700000L).build();
//
//        SubService updateSubService = adminService.updateSubService(subService);
//
//        Assertions.assertEquals(subService.getDescription(), updateSubService.getDescription());
//        Assertions.assertEquals(subService.getBasePrice(), updateSubService.getBasePrice());
//    }
//
//    @Test
//    @Order(9)
//    void givenMethodCalledUpdateSubService_whenSubServiceDoesNotExist_thenThrowException() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceNotFound")
//                .setDescription("UpdateSubService....")
//                .setBasePrice(5000000L).build();
//
//        Assertions.assertThrows(NotFoundException.class, () -> adminService.updateSubService(subService));
//    }
//
//    @Test
//    @Order(10)
//    void givenMethodCalledUpdateSubService_whenChangeDescriptionButBasePriceIsNull_thenDescriptionChangedButBasePriceNotChanged() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceName")
//                .setDescription("*******UpdateSubServiceDescription....")
//                .build();
//
//        SubService updateSubService = adminService.updateSubService(subService);
//
//        Assertions.assertEquals(subService.getDescription(), updateSubService.getDescription());
//        Assertions.assertNotEquals(subService.getBasePrice(), updateSubService.getBasePrice());
//    }
//
//    @Test
//    @Order(11)
//    void givenMethodCalledUpdateSubService_whenDescriptionIsNullAndBasePriceIsNull_thenNothingChanged() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceName")
//                .build();
//
//        SubService updateSubService = adminService.updateSubService(subService);
//
//        Assertions.assertNotEquals(subService.getDescription(), updateSubService.getDescription());
//        Assertions.assertNotEquals(subService.getBasePrice(), updateSubService.getBasePrice());
//    }
//
//    @Test
//    @Order(12)
//    void givenMethodCalledUpdateSubService_whenDescriptionIsBlankAndBasePriceIsNull_thenNothingChanged() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceName")
//                .setDescription("")
//                .build();
//
//        SubService updateSubService = adminService.updateSubService(subService);
//
//        Assertions.assertNotEquals(subService.getDescription(), updateSubService.getDescription());
//        Assertions.assertNotEquals(subService.getBasePrice(), updateSubService.getBasePrice());
//    }
//
//    @Test
//    @Order(13)
//    void givenMethodCalledUpdateSubService_whenDescriptionIsBlankAndChangedBasePrice_thenBasePriceChanged() {
//        SubService subService = SubService.builder()
//                .setName("SubServiceName")
//                .setDescription("")
//                .setBasePrice(8000000L)
//                .build();
//
//        SubService updateSubService = adminService.updateSubService(subService);
//
//        Assertions.assertNotEquals(subService.getDescription(), updateSubService.getDescription());
//        Assertions.assertEquals(subService.getBasePrice(), updateSubService.getBasePrice());
//    }
//
//    @Test
//    @Order(14)
//    void giveMethodCalledRegisterSubService_whenEveryThingIsOk2_thenReturnProperResult() {
//        SubService subService = SubService.builder()
//                .setName("SubService").setDescription("SubServiceDescription")
//                .setBasePrice(500000L).build();
//
//        SubService registerSubService = adminService.registerSubService(subService, "mainServiceTest");
//
//        Assertions.assertEquals(1L, registerSubService.getMainService().getId());
//    }
//
//
//}