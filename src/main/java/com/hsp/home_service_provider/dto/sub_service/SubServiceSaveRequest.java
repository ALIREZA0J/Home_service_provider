package com.hsp.home_service_provider.dto.sub_service;

import com.hsp.home_service_provider.dto.main_service.MainServiceSaveRequest;

public record SubServiceSaveRequest(String name,
                                    String description,
                                    Long basePrice,
                                    MainServiceSaveRequest mainService) {
}
