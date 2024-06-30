package com.hsp.home_service_provider.dto.sub_service;

import com.hsp.home_service_provider.dto.main_service.MainServiceResponse;

public record SubServiceResponse(String name,
                                 String description,
                                 Long basePrice,
                                 MainServiceResponse mainService) {
}
