package com.lima.api.soccer.application.port.payment.input;

import com.lima.api.soccer.application.dto.payment.request.PaymentRequestDTO;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;

public interface RegisterPaymentInputPort {

    PaymentResponseDTO execute(PaymentRequestDTO paymentRequestDTO);
}
