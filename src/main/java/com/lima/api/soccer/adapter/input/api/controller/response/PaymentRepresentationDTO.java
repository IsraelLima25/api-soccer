package com.lima.api.soccer.adapter.input.api.controller.response;

import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;

import java.time.LocalDate;

public record PaymentRepresentationDTO(
        String code,
        LocalDate date,
        String player,
        TypePaymentIndicator type,
        StatusPaymentIndicator status
) { }
