package com.lima.api.soccer.application.port.payment.input;

import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;

public interface FindPaymentByCodeInputPort {

    PaymentResponseDTO execute (String idPayment);
}
