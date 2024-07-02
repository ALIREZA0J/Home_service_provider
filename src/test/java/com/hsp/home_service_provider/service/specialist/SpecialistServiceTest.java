//package com.hsp.home_service_provider.service.specialist;
//
//import com.hsp.home_service_provider.exception.*;
//import com.hsp.home_service_provider.model.Specialist;
//import com.hsp.home_service_provider.model.enums.SpecialistStatus;
//import com.hsp.home_service_provider.repository.specialist.SpecialistRepository;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class SpecialistServiceTest {
//
//    @Autowired
//    SpecialistService specialistService;
//    @Autowired
//    SpecialistRepository specialistRepository;
//
//
//
//    @Test
//    @Order(1)
//    void givenMethodCalledRegister_whenEveryThingIsOk_thenReturnProperResult(){
//        Specialist specialist = Specialist.builder()
//                .setFirstName("SpecialistTest").setLastName("SpecialistTest")
//                .setGmail("specialisttest@specialistGmail.com").setPassword("SpecialistTest123#")
//                .setRegistrationDate(LocalDate.now())
//                .build();
//
//        Specialist register = specialistService.register(specialist,
//                "D:\\java\\java.project\\home_service_provider\\src\\main\\java\\com\\hsp\\" +
//                        "home_service_provider\\utility\\alaki\\photo_2024-05-25_09-06-15.jpg");
//
//        assertEquals(register.getScore(),0L);
//    }
//    @Test
//    @Order(2)
//    void givenMethodCalledRegister_whenDataNotValid_thenThrowNotValidException(){
//        Specialist specialist = Specialist.builder()
//                .setFirstName("Specialist123").setLastName("213")
//                .setGmail("NOT.VALID.GMAIL@specialistGmail.com").setPassword("not-correct-password")
//                .setRegistrationDate(LocalDate.now())
//                .build();
//
//        assertThrows(NotValidException.class,() -> specialistService.register(specialist,
//                "D:\\java\\java.project\\home_service_provider\\src\\main\\java\\com\\hsp\\" +
//                        "home_service_provider\\utility\\alaki\\photo_2024-05-25_09-06-15.jpg"));
//    }
//
//    @Test
//    @Order(3)
//    void givenMethodCalledRegister_whenFilePhotoNotExist_thenThrowFileNotFoundException(){
//        Specialist specialist = Specialist.builder()
//                .setFirstName("SpecialistTest").setLastName("SpecialistTest")
//                .setGmail("specialist@specialistGmail.com").setPassword("SpecialistTest123#")
//                .setRegistrationDate(LocalDate.now())
//                .build();
//        assertThrows(FileNotFoundException.class,() -> specialistService.register(specialist,
//                "D:\\java\\java.project\\home_service_provider\\src\\main\\java\\com\\hsp\\" +
//                        "home_service_provider\\utility\\alaki\\not_found_file.jpg"));
//    }
//
//    @Test
//    @Order(4)
//    void givenMethodCalledRegister_whenSizeOfFileMoreThanOver300Kilobits_thenThrowException(){
//        Specialist specialist = Specialist.builder()
//                .setFirstName("SpecialistTest").setLastName("SpecialistTest")
//                .setGmail("specialist@specialistGmail.com").setPassword("SpecialistTest123#")
//                .setRegistrationDate(LocalDate.now())
//                .build();
//        assertThrows(AvatarException.class,() -> specialistService.register(specialist,
//                "D:\\java\\java.project\\home_service_provider\\src\\main\\java\\com\\hsp\\" +
//                        "home_service_provider\\utility\\alaki\\sizeMoreThan300k.JPG"));
//    }
//
//    @Test
//    @Order(5)
//    void givenMethodCalledRegister_whenPhotoTypeIsPng_thenThrowException(){
//        Specialist specialist = Specialist.builder()
//                .setFirstName("SpecialistTest").setLastName("SpecialistTest")
//                .setGmail("specialist@specialistGmail.com").setPassword("SpecialistTest123#")
//                .setRegistrationDate(LocalDate.now())
//                .build();
//        assertThrows(AvatarException.class,() -> specialistService.register(specialist,
//                "D:\\java\\java.project\\home_service_provider\\src\\main\\java\\com\\hsp\\" +
//                        "home_service_provider\\utility\\alaki\\png.png"));
//    }
//
//    @Test
//    @Order(6)
//    void givenMethodCalledRegister_whenHtmlFileSend_thenThrowException(){
//        Specialist specialist = Specialist.builder()
//                .setFirstName("SpecialistTest").setLastName("SpecialistTest")
//                .setGmail("specialist@specialistGmail.com").setPassword("SpecialistTest123#")
//                .setRegistrationDate(LocalDate.now())
//                .build();
//        assertThrows(AvatarException.class,() -> specialistService.register(specialist,
//                "D:\\java\\java.project\\home_service_provider\\src\\main\\java\\com\\hsp\\" +
//                        "home_service_provider\\utility\\alaki\\asd.html"));
//    }
//
//    @Test
//    @Order(7)
//    void givenMethodCalledChangePassword_whenSpecialistNotAccepted_thenThrowException(){
//        String specialistGmail = "specialisttest@specialistGmail.com";
//        String pass1 = "Specialist@123TEST";
//        String pass2 = "Specialist@123TEST";
//
//        assertThrows(SpecialistException.class,() -> specialistService.changePassword(specialistGmail,pass1,pass2));
//    }
//
//    @Test
//    @Order(8)
//    void givenMethodCalledChangeSpecialistStatusToAccept_whenEveryThingIsOk_thenReturnProperResult(){
//        String specialistGmail = "specialisttest@specialistGmail.com";
//
//        specialistService.changeSpecialistStatusToAccept(specialistGmail);
//        Specialist specialist = specialistService.findByGmail(specialistGmail);
//
//        assertEquals(SpecialistStatus.ACCEPTED,specialist.getSpecialistStatus());
//    }
//
//
//    @Test
//    @Order(9)
//    void givenMethodCalledChangePassword_whenPasswordAndRepeatPasswordNotEqual_thenThrowException(){
//        String specialistGmail = "specialisttest@specialistGmail.com";
//        String pass1 = "Specialist@123TEST";
//        String pass2 = "specialist@123TEST";
//
//        assertThrows(MismatchException.class,() -> specialistService.changePassword(specialistGmail,pass1,pass2));
//    }
//
//    @Test
//    @Order(10)
//    void givenMethodCalledChangePassword_whenEveryThingIsOk_thenReturnProperResult(){
//        String specialistGmail = "specialisttest@specialistGmail.com";
//        String pass1 = "Specialist@123TEST";
//        String pass2 = "Specialist@123TEST";
//
//        specialistService.changePassword(specialistGmail,pass1,pass2);
//        Specialist specialist = specialistService.findByGmail(specialistGmail);
//
//        assertEquals(pass1,specialist.getPassword());
//    }
//
//    @Test
//    @Order(11)
//    void givenMethodCalledRegister_whenGmailIsDuplicate_thenThrowException(){
//        Specialist specialist = Specialist.builder()
//                .setFirstName("SpecialistTest").setLastName("SpecialistTest")
//                .setGmail("specialisttest@specialistGmail.com").setPassword("SpecialistTest123#")
//                .setRegistrationDate(LocalDate.now())
//                .build();
//
//        assertThrows(SpecialistException.class, () -> specialistService.register(specialist,
//                "D:\\java\\java.project\\home_service_provider\\src\\main\\java\\com\\hsp\\" +
//                        "home_service_provider\\utility\\alaki\\photo_2024-05-25_09-06-15.jpg"));
//    }
//
//}