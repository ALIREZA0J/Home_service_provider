package com.hsp.home_service_provider.dto.customer;

public record CustomerSaveRequest(String firstName,
                                  String lastName,
                                  String gmail,
                                  String password) {
}
