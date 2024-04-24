package com.lima.api.soccer.adapter.output.database.repository.payment;

import com.lima.api.soccer.adapter.output.database.model.PaymentModel;

import java.util.Optional;

public interface PaymentRepository {

    PaymentModel save(PaymentModel paymentModel);
    Optional<PaymentModel> findByCode(String codePayment);
}
