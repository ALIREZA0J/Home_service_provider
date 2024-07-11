package com.hsp.home_service_provider.dto.customer;

import java.time.LocalDate;

public record CustomerFilter(String firstName,
                             String lastName,
                             String gmail,
                             LocalDate registrationDate) {
}
