package com.hsp.home_service_provider.service.subservice;

import com.hsp.home_service_provider.exception.AbsenceException;
import com.hsp.home_service_provider.exception.DuplicateException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.exception.SubServiceException;
import com.hsp.home_service_provider.model.MainService;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.repository.subservice.SubServiceRepository;
import com.hsp.home_service_provider.service.mainservice.MainServiceService;
import com.hsp.home_service_provider.service.specialist.SpecialistService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubServiceServiceTest {

    @Autowired
    SubServiceService subServiceService;
    @Autowired
    SubServiceRepository subServiceRepository;
    @Autowired
    MainServiceService mainServiceService;
    @Autowired
    SpecialistService specialistService;

    @Test
    @Order(1)
    void givenMethodCalledAddSpecialistToSubService_whenEveryThingIsOk_thenReturnProperResult(){
        Specialist specialist = specialistService.findByGmail("specialisttest@gmail.com");
        SubService subService = subServiceService.findByName("SubServiceName");

        subServiceService.addSpecialistToSubService(subService,specialist);
        Specialist afterSaveToSubService = specialistService.findByGmail("specialisttest@gmail.com");

        assertTrue(afterSaveToSubService.getSubServices().size()>specialist.getSubServices().size());
    }

    @Test
    @Order(2)
    void givenMethodCalledAddSpecialistToSubService_whenSpecialistIsInSubService_thenThrowException(){
        Specialist specialist = specialistService.findByGmail("specialisttest@gmail.com");
        SubService subService = subServiceService.findByName("SubServiceName");

        assertThrows(DuplicateException.class,() -> subServiceService.addSpecialistToSubService(subService,specialist));
    }

    @Test
    @Order(3)
    void givenMethodCalledRemoveSpecialistFromSubService_whenEveryThingIsOk_thenReturnProperResult(){
        Specialist specialist = specialistService.findByGmail("specialisttest@gmail.com");
        SubService subService = subServiceService.findByName("SubServiceName");

        subServiceService.removeSpecialistFromSubService(subService,specialist);
        Specialist afterSaveToSubService = specialistService.findByGmail("specialisttest@gmail.com");

        assertTrue(afterSaveToSubService.getSubServices().size()<specialist.getSubServices().size());
    }

    @Test
    @Order(4)
    void givenMethodCalledRemoveSpecialistFromSubService_whenSpecialistIsNotInSubService_thenReturnProperResult(){
        Specialist specialist = specialistService.findByGmail("specialisttest@gmail.com");
        SubService subService = subServiceService.findByName("SubServiceName");

        assertThrows(AbsenceException.class,() -> subServiceService.removeSpecialistFromSubService(subService,specialist));
    }

    @Test
    @Order(5)
    void giveMethodCalledRegister_whenEveryThingIsOk_thenReturnProperResult() {
        MainService mainServiceTest = mainServiceService.findByName("mainServiceTest");

        SubService subService = SubService.builder()
                .setName("SubServiceNameTestInSubService").setDescription("SubServiceDescriptionTestInSubService")
                .setBasePrice(500000L).setMainService(mainServiceTest).build();

        SubService registerSubService = subServiceService.register(subService);

        Assertions.assertEquals(1L, registerSubService.getMainService().getId());
    }

    @Test
    @Order(6)
    void giveMethodCalledRegisterSubService_whenNameOfSubServiceIsDuplicate_thenThrowException() {
        MainService mainServiceTest = mainServiceService.findByName("mainServiceTest");
        SubService subService = SubService.builder()
                .setName("SubServiceNameTestInSubService").setDescription("SubServiceDescriptionTestInSubService")
                .setBasePrice(500000L).setMainService(mainServiceTest).build();

        Assertions.assertThrows(DuplicateException.class,
                () -> subServiceService.register(subService));
    }


    @Test
    @Order(7)
    void givenMethodCalledRegisterSubService_whenBasePriceIsLessThanThe300_000_thenThrowException() {
        MainService mainServiceTest = mainServiceService.findByName("mainServiceTest");
        SubService subService = SubService.builder()
                .setName("dsasdasd").setDescription("asdsadsad").setMainService(mainServiceTest)
                .setBasePrice(2000L).build();

        Assertions.assertThrows(SubServiceException.class,
                () -> subServiceService.register(subService));
    }

    @Test
    @Order(8)
    void givenMethodCalledUpdateSubService_whenChangeDescriptionAndBasePrice_thenReturnProperResult() {
        SubService subService = SubService.builder()
                .setName("SubServiceNameTestInSubService")
                .setDescription("UpdateSubServiceDescription....")
                .setBasePrice(700000L).build();

        SubService updateSubService = subServiceService.update(subService);

        Assertions.assertEquals(subService.getDescription(), updateSubService.getDescription());
        Assertions.assertEquals(subService.getBasePrice(), updateSubService.getBasePrice());
    }

    @Test
    @Order(9)
    void givenMethodCalledUpdateSubService_whenSubServiceDoesNotExist_thenThrowException() {
        SubService subService = SubService.builder()
                .setName("SubServiceNotFound")
                .setDescription("UpdateSubService....")
                .setBasePrice(5000000L).build();

        Assertions.assertThrows(NotFoundException.class, () -> subServiceService.update(subService));
    }

    @Test
    @Order(10)
    void givenMethodCalledUpdateSubService_whenChangeDescriptionButBasePriceIsNull_thenDescriptionChangedButBasePriceNotChanged() {
        SubService subService = SubService.builder()
                .setName("SubServiceNameTestInSubService")
                .setDescription("*******UpdateSubServiceDescription....")
                .build();

        SubService updateSubService = subServiceService.update(subService);

        Assertions.assertEquals(subService.getDescription(), updateSubService.getDescription());
        Assertions.assertNotEquals(subService.getBasePrice(), updateSubService.getBasePrice());
    }

    @Test
    @Order(11)
    void givenMethodCalledUpdateSubService_whenDescriptionIsNullAndBasePriceIsNull_thenNothingChanged() {
        SubService subService = SubService.builder()
                .setName("SubServiceNameTestInSubService")
                .build();

        SubService updateSubService = subServiceService.update(subService);

        Assertions.assertNotEquals(subService.getDescription(), updateSubService.getDescription());
        Assertions.assertNotEquals(subService.getBasePrice(), updateSubService.getBasePrice());
    }

    @Test
    @Order(12)
    void givenMethodCalledUpdateSubService_whenDescriptionIsBlankAndBasePriceIsNull_thenNothingChanged() {
        SubService subService = SubService.builder()
                .setName("SubServiceNameTestInSubService")
                .setDescription("")
                .build();

        SubService updateSubService = subServiceService.update(subService);

        Assertions.assertNotEquals(subService.getDescription(), updateSubService.getDescription());
        Assertions.assertNotEquals(subService.getBasePrice(), updateSubService.getBasePrice());
    }

    @Test
    @Order(13)
    void givenMethodCalledUpdateSubService_whenDescriptionIsBlankAndChangedBasePrice_thenBasePriceChanged() {
        SubService subService = SubService.builder()
                .setName("SubServiceNameTestInSubService")
                .setDescription("")
                .setBasePrice(8000000L)
                .build();

        SubService updateSubService = subServiceService.update(subService);

        Assertions.assertNotEquals(subService.getDescription(), updateSubService.getDescription());
        Assertions.assertEquals(subService.getBasePrice(), updateSubService.getBasePrice());
    }

    @Test
    @Order(14)
    void givenMethodCalledAddSpecialistToSubService_whenEveryThingIsOk2_thenReturnProperResult(){
        Specialist specialist = specialistService.findByGmail("specialisttest@gmail.com");
        SubService subService = subServiceService.findByName("SubServiceName");

        subServiceService.addSpecialistToSubService(subService,specialist);
        Specialist afterSaveToSubService = specialistService.findByGmail("specialisttest@gmail.com");

        assertTrue(afterSaveToSubService.getSubServices().size()>specialist.getSubServices().size());
    }



}