package com.hsp.home_service_provider.service.offer;

import com.hsp.home_service_provider.exception.AbsenceException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.exception.OfferException;
import com.hsp.home_service_provider.exception.OrderException;
import com.hsp.home_service_provider.model.Offer;
import com.hsp.home_service_provider.model.Order;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.enums.OfferStatus;
import com.hsp.home_service_provider.model.enums.OrderStatus;
import com.hsp.home_service_provider.repository.offer.OfferRepository;
import com.hsp.home_service_provider.service.order.OrderService;
import com.hsp.home_service_provider.service.specialist.SpecialistService;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final OrderService orderService;
    private final SpecialistService specialistService;
    private final Validation validation;

    public Offer register(Offer offer){
        Specialist specialist = specialistService.findByGmail(offer.getSpecialist().getGmail());
        Order order = orderService.findById(offer.getOrder().getId());
        if (!specialist.getSubServices().contains(order.getSubService()))
            throw new AbsenceException("Specialist sub-services do not include ordered sub-services");
        if (!(order.getOrderStatus().equals(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST) ||
                order.getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION)))
            throw new OrderException("Order status is unavailable for offer.");
        offer.setSpecialist(specialist);
        offer.setOrder(order);
        if (offer.getDaysOfWork() == null || !validation.checkPositiveNumber(offer.getDaysOfWork().longValue()))
            throw new OfferException("days of work must be more than 0.");
        validation.checkProposedPriceNotLessThanSubService(offer.getOfferPrice(),
                offer.getOrder().getSubService().getBasePrice());
        offer.setOfferStatus(OfferStatus.WAITING);
        offer.setTimeToRegisterOffer(LocalDateTime.now());
        orderService.changeStatusOfOrderToWaitingForSpecialistSelection(offer.getOrder().getId());
        return offerRepository.save(offer);
    }

    public Offer findById(Long id){
        return offerRepository.findById(id).orElseThrow(()-> new NotFoundException("Offer with (id:"+id+") not found."));
    }

    public List<Offer> displayOffersOfOrderSortByPriceAndScore(Long orderId){
        Order order = orderService.findById(orderId);
        List<Offer> offersOfOrderIsWaitingStatus = findOffersOfOrderIsWaitingStatus(order);
        return offersOfOrderIsWaitingStatus.stream()
                .sorted(Comparator.comparing(Offer::getOfferPrice))
                .sorted(Comparator.comparing(o -> o.getSpecialist().getScore()))
                .toList();
    }

    public void acceptedOffer(Long id){
        Offer offer = findById(id);
        if (offer.getOfferStatus().equals(OfferStatus.WAITING)){
            offer.setOfferStatus(OfferStatus.ACCEPTED);
            offerRepository.save(offer);
            orderService.changeStatusOfOrderToWaitingForSpecialistGoToCustomerPlace(offer.getOrder().getId());
            rejectedOtherOfferForOrder(offer.getOrder());
        }
    }

    public void rejectedOtherOfferForOrder(Order order){
        List<Offer> offersOfOrderIsWaitingStatus = findOffersOfOrderIsWaitingStatus(order);
        if (offersOfOrderIsWaitingStatus != null){
            for (Offer offer : offersOfOrderIsWaitingStatus) {
                offer.setOfferStatus(OfferStatus.REJECTED);
                offerRepository.save(offer);
            }
        }
    }

    public List<Offer> findOffersOfOrderIsWaitingStatus(Order order) {
        return offerRepository.findOffersByOrderAndOfferStatus(order, OfferStatus.WAITING);
    }


}
