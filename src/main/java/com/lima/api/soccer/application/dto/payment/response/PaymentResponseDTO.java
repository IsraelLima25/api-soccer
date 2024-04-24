package com.lima.api.soccer.application.dto.payment.response;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;

import java.time.LocalDate;

public record PaymentResponseDTO (Long id, String code, LocalDate datePayment, PlayerResponseDTO playerResponseDTO, TypePaymentIndicator typePayment, StatusPaymentIndicator statusPaymentIndicator) { }
