package com.hsp.home_service_provider.dto.specialist;

import com.hsp.home_service_provider.dto.sub_service.SubServiceRequest;

import java.time.LocalDate;

public record SpecialistFilter (String firstName,
                                String lastName,
                                String gmail,
                                LocalDate registrationDate,
                                Double score,
                                SubServiceRequest subService)
{
}
