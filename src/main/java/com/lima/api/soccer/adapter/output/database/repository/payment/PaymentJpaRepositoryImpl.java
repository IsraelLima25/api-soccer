package com.lima.api.soccer.adapter.output.database.repository.payment;

import com.lima.api.soccer.adapter.output.database.JpaRepository.PaymentJPARepository;
import com.lima.api.soccer.adapter.output.database.model.PaymentModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentJpaRepositoryImpl implements PaymentRepository{

    private final PaymentJPARepository paymentJPARepository;

    public PaymentJpaRepositoryImpl(PaymentJPARepository paymentJPARepository) {
        this.paymentJPARepository = paymentJPARepository;
    }

    @Override
    public PaymentModel save(PaymentModel paymentModel) {
        return paymentJPARepository.save(paymentModel);
    }

    @Override
    public Optional<PaymentModel> findByCode(String codePayment) {
        return paymentJPARepository.findByCode(codePayment);
    }
}
