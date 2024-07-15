package com.hsp.home_service_provider.dto.payment;

import com.hsp.home_service_provider.utility.ValidationCard;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    @NotNull
    @ValidationCard
    @Min(value = 1000_0000_0000_0000L)
    private Long cardNumber;

    @NotNull
    @Range(min = 100, max = 9999, message = "Cvv2 out of range")
    private Integer cvv2;

    @NotNull
    @Range(min = 1, max = 12)
    private Integer month;

    @NotNull
    @Range(min = 1403,max = 1410)
    private Integer year;

    @NotNull
    @Range(min = 1000000 ,max = 9999999)
    private Long password;

    @NotNull
    private String captcha;

    @NotNull
    private Long orderId;
}
