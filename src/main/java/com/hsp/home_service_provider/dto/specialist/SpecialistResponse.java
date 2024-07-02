package com.hsp.home_service_provider.dto.specialist;

import com.hsp.home_service_provider.dto.sub_service.SubServiceResponse;
import com.hsp.home_service_provider.model.enums.SpecialistStatus;

import java.util.Set;

public record SpecialistResponse(String firstName,
                                 String lastName,
                                 String gmail,
                                 Long credit,
                                 Double score,
                                 SpecialistStatus specialistStatus,
                                 Set<SubServiceResponse> subServices) {
}
