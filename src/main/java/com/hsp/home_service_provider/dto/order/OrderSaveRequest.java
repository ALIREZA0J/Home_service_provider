package com.hsp.home_service_provider.dto.order;

import com.hsp.home_service_provider.dto.address.AddressRequest;
import com.hsp.home_service_provider.dto.customer.CustomerRequest;
import com.hsp.home_service_provider.dto.sub_service.SubServiceRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public record OrderSaveRequest(String description,
                               Long proposedPrice,
                               LocalDate dateOfWork,
                               LocalTime timeOfWork,
                               CustomerRequest customer,
                               AddressRequest address,
                               SubServiceRequest subService) {
}
