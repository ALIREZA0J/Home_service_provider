package com.hsp.home_service_provider.dto.order;

import com.hsp.home_service_provider.dto.address.AddressResponse;
import com.hsp.home_service_provider.dto.customer.CustomerResponse;
import com.hsp.home_service_provider.dto.sub_service.SubServiceResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public record OrderResponse (String description,
                             Long proposedPrice,
                             LocalDate dateOfWork,
                             LocalTime timeOfWork,
                             CustomerResponse customer,
                             AddressResponse address,
                             SubServiceResponse subService){
}
