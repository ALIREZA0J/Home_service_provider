package com.hsp.home_service_provider.dto.address;

import com.hsp.home_service_provider.model.enums.City;

public record AddressSaveRequest(City city,
                                 String street,
                                 String alley,
                                 Integer plaque
                                ) {
}
