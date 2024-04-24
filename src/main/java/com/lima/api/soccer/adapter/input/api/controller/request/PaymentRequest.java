package com.lima.api.soccer.adapter.input.api.controller.request;

import com.lima.api.soccer.application.dto.payment.request.PaymentRequestDTO;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PaymentRequest(
        @NotNull LocalDate datePayment,
        @NotBlank String codePlayer,
        @NotNull TypePaymentIndicator typePaymentIndicator) {
    public PaymentRequestDTO toPaymentRequestDTO() {
        return new PaymentRequestDTO(datePayment, codePlayer, typePaymentIndicator);
    }
}
