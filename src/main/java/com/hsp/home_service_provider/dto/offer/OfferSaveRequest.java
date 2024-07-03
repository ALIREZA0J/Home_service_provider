package com.hsp.home_service_provider.dto.offer;

import com.hsp.home_service_provider.dto.order.OrderRequest;
import com.hsp.home_service_provider.dto.specialist.SpecialistRequest;

import java.time.LocalTime;

public record OfferSaveRequest(Long offerPrice,
                               Integer daysOfWork,
                               LocalTime durationOfWork,
                               OrderRequest order,
                               SpecialistRequest specialist) {
}
