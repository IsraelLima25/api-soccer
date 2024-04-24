package com.lima.api.soccer.application.port.payment.output;

import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;

import java.util.Optional;

public interface FindPaymentByCodeOutputPort {

    Optional<PaymentResponseDTO> findPaymentByCode(String codePayment);
}
