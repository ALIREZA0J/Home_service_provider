package com.hsp.home_service_provider.dto.exception;

import java.time.LocalDateTime;

public record ExceptionDTO(String message, LocalDateTime localDateTime) {
}
