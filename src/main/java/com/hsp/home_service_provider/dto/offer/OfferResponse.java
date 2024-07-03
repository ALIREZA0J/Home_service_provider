package com.hsp.home_service_provider.dto.offer;

import com.hsp.home_service_provider.dto.order.OrderResponse;
import com.hsp.home_service_provider.dto.specialist.SpecialistResponseOffer;

import java.time.LocalTime;

public record OfferResponse(Long offerPrice,
                            Integer daysOfWork,
                            LocalTime durationOfWork,
                            OrderResponse order,
                            SpecialistResponseOffer specialist) {
}
