package com.hsp.home_service_provider.service.offer;

import com.hsp.home_service_provider.exception.AbsenceException;
import com.hsp.home_service_provider.exception.OfferException;
import com.hsp.home_service_provider.exception.OrderException;
import com.hsp.home_service_provider.exception.ProposedPriceException;
import com.hsp.home_service_provider.model.Offer;
import com.hsp.home_service_provider.model.enums.OfferStatus;
import com.hsp.home_service_provider.repository.offer.OfferRepository;
import com.hsp.home_service_provider.service.order.OrderService;
import com.hsp.home_service_provider.service.specialist.SpecialistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

@SpringBootTest
class OfferServiceTest {

    @Autowired
    OfferService offerService;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    SpecialistService specialistService;

    @Test
    @Order(1)
    void givenMethodCalledRegister_whenEveryThingIsOk_thenReturnProperResult(){
        Offer offer = Offer.builder()
                .setDaysOfWork(2).setOfferPrice(9000000L)
                .setDurationOfWork(LocalTime.of(2,0))
                .build();

        Offer register = offerService.register(offer,1L,1L);

        Assertions.assertEquals(register.getOfferStatus(), OfferStatus.WAITING);
    }
    @Test
    @Order(2)
    void givenMethodCalledRegister_whenDaysOfWorkIsNull_thenThrowException(){
        Offer offer = Offer.builder()
                .setOfferPrice(9000000L)
                .setDurationOfWork(LocalTime.of(2,0))
                .build();

        Assertions.assertThrows(OfferException.class, () -> offerService.register(offer,1L,1L));
    }
    @Test
    @Order(3)
    void givenMethodCalledRegister_whenDaysOfWorkIs0_thenThrowException(){
        Offer offer = Offer.builder()
                .setOfferPrice(9000000L).setDaysOfWork(0)
                .setDurationOfWork(LocalTime.of(2,0))
                .build();

        Assertions.assertThrows(OfferException.class, () -> offerService.register(offer,1L,1L));
    }

    @Test
    @Order(4)
    void givenMethodCalledRegister_whenProposedPriceIsLessThanBasePrice_thenThrowException(){
        Offer offer = Offer.builder()
                .setOfferPrice(200000L).setDaysOfWork(2)
                .setDurationOfWork(LocalTime.of(2,0))
                .build();
        Assertions.assertThrows(ProposedPriceException.class, () -> offerService.register(offer,1L,1L));
    }

    @Test
    @Order(5)
    void givenMethodCalledAcceptedOffer_whenEveryThingIsOk_thenReturnProperResult(){
        Offer offerBeforeAccepted = offerService.findById(1L);
        offerService.acceptedOffer(1L);
        Offer offerAfterAccepted = offerService.findById(1L);

        Assertions.assertNotEquals(offerBeforeAccepted.getOfferStatus(),offerAfterAccepted.getOfferStatus());
    }

    @Test
    @Order(6)
    void givenMethodCalledRegister_whenSubServiceOfOrderDidNotContainSpecialist_thenThrowException(){
        Offer offer = Offer.builder()
                .setOfferPrice(9000000L)
                .setDaysOfWork(2)
                .setDurationOfWork(LocalTime.of(2,0))
                .build();
        Assertions.assertThrows(AbsenceException.class, () -> offerService.register(offer,1L,2L));
    }

    @Test
    @Order(7)
    void givenMethodCalledRegister_whenStatusOfOrderNotInWaitingForSuggestionSpecialistAndSelectionSpecialist_thenThrowException(){
        Offer offer = Offer.builder()
                .setOfferPrice(9000000L)
                .setDaysOfWork(2)
                .setDurationOfWork(LocalTime.of(2,0))
                .build();
        Assertions.assertThrows(OrderException.class, () -> offerService.register(offer,1L,3L));
    }

}