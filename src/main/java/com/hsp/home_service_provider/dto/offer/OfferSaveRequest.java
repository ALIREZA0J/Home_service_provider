package com.hsp.home_service_provider.dto.offer;

import com.hsp.home_service_provider.dto.order.OrderRequest;

import java.time.LocalTime;

public record OfferSaveRequest(Long offerPrice,
                               Integer daysOfWork,
                               LocalTime durationOfWork,
                               OrderRequest order) {
}
