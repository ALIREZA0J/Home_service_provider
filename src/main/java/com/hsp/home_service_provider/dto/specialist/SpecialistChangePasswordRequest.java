package com.hsp.home_service_provider.dto.specialist;

public record SpecialistChangePasswordRequest (String gmail,
                                               String password1,
                                               String password2) {
}
