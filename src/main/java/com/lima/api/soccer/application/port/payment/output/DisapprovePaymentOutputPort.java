package com.lima.api.soccer.application.port.payment.output;

import com.lima.api.soccer.application.entity.Payment;

public interface DisapprovePaymentOutputPort {

    void updateDisapprovePayment(Payment payment);
}
