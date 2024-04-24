package com.lima.api.soccer.application.port.payment.output;

import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.entity.Payment;

public interface RegisterPaymentOutputPort {

    PaymentResponseDTO registerPayment(Payment payment);
}
