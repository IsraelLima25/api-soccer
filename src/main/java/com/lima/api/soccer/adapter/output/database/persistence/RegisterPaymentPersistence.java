package com.lima.api.soccer.adapter.output.database.persistence;

import com.lima.api.soccer.adapter.output.database.model.PaymentModel;
import com.lima.api.soccer.adapter.output.database.model.PlayerModel;
import com.lima.api.soccer.adapter.output.database.repository.payment.PaymentRepository;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.entity.Payment;
import com.lima.api.soccer.application.port.payment.output.RegisterPaymentOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RegisterPaymentPersistence implements RegisterPaymentOutputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPaymentPersistence.class);

    private final PaymentRepository paymentRepository;

    public RegisterPaymentPersistence(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponseDTO registerPayment(Payment payment) {
        LOGGER.info("Start payment save DB");
        PlayerModel playerModel = new PlayerModel(payment.getPlayer().getId(), payment.getPlayer().getCode(), payment.getPlayer().getName(), payment.getPlayer().getDob(), payment.getPlayer().getRg());
        PaymentModel paymentModel = new PaymentModel(payment.getDatePayment(), playerModel, payment.getTypePaymentIndicator(), payment.getStatusPaymentIndicator());
        return paymentRepository.save(paymentModel).toPaymentResponseDTO();
    }

}
