package com.hsp.home_service_provider.dto.sub_service;

public record SubServiceUpdateRequest(String name,
                                      String description,
                                      Long basePrice) {
}
