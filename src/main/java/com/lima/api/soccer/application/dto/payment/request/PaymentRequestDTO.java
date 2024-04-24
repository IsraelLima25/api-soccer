package com.lima.api.soccer.application.dto.payment.request;

import com.lima.api.soccer.application.indicator.TypePaymentIndicator;

import java.time.LocalDate;

public record PaymentRequestDTO (LocalDate datePayment, String codePlayer, TypePaymentIndicator typePayment) { }
