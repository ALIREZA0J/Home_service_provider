package com.hsp.home_service_provider.dto.specialist;

public record SpecialistChangePasswordRequest (String gmail,
                                               String password,
                                               String newPassword,
                                               String confirmNewPassword) {
}
