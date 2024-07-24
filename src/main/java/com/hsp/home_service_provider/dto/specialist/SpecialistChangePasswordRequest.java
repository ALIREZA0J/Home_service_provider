package com.hsp.home_service_provider.dto.specialist;

public record SpecialistChangePasswordRequest (String oldPassword,
                                               String newPassword,
                                               String confirmNewPassword) {
}
