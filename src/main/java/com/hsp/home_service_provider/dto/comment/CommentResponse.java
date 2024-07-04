package com.hsp.home_service_provider.dto.comment;

import com.hsp.home_service_provider.dto.customer.CustomerOtherResponse;
import com.hsp.home_service_provider.dto.offer.OfferResponse;

public record CommentResponse(Integer score,
                              String message,
                              CustomerOtherResponse customer,
                              OfferResponse offer) {
}
