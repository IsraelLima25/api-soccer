package com.lima.api.soccer.adapter.output.database.persistence;

import com.lima.api.soccer.adapter.output.database.model.PaymentModel;
import com.lima.api.soccer.adapter.output.database.repository.payment.PaymentRepository;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.port.payment.output.FindPaymentByCodeOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindPaymentByCodePersistence implements FindPaymentByCodeOutputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindPaymentByCodePersistence.class);

    private final PaymentRepository paymentRepository;

    public FindPaymentByCodePersistence(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<PaymentResponseDTO> findPaymentByCode(String codePayment) {
        LOGGER.info("Start find payment DB");
        Optional<PaymentModel> optionalPaymentModel = paymentRepository.findByCode(codePayment);
        return optionalPaymentModel.map(PaymentModel::toPaymentResponseDTO);
    }
}
