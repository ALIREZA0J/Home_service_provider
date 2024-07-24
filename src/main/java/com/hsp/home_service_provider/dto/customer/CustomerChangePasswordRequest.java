package com.hsp.home_service_provider.dto.customer;

public record CustomerChangePasswordRequest(
                                            String oldPassword,
                                            String newPassword,
                                            String confirmNewPassword) {
}
