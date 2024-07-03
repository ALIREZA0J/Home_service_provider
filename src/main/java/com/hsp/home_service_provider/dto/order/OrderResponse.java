package com.hsp.home_service_provider.dto.order;

import com.hsp.home_service_provider.dto.address.AddressResponse;
import com.hsp.home_service_provider.dto.customer.CustomerOrderResponse;
import com.hsp.home_service_provider.dto.sub_service.SubServiceOrderResponse;
import com.hsp.home_service_provider.model.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record OrderResponse (String description,
                             Long proposedPrice,
                             LocalDate dateOfWork,
                             LocalTime timeOfWork,
                             OrderStatus orderStatus,
                             CustomerOrderResponse customer,
                             AddressResponse address,
                             SubServiceOrderResponse subService){
}
