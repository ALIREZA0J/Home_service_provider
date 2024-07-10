package com.hsp.home_service_provider.dto.customer;

public record CustomerChangePasswordRequest(String gmail,
                                            String password,
                                            String newPassword,
                                            String confirmNewPassword) {
}
