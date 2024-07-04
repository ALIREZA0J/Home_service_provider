package com.hsp.home_service_provider.dto.comment;

import com.hsp.home_service_provider.dto.customer.CustomerRequest;
import com.hsp.home_service_provider.dto.offer.OfferRequest;

public record CommentSaveRequest(Integer score,
                                 String message,
                                 CustomerRequest customer,
                                 OfferRequest offer) {
}
