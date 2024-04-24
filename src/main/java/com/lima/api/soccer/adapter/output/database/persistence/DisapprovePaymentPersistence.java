package com.lima.api.soccer.adapter.output.database.persistence;

import com.lima.api.soccer.adapter.output.database.model.PaymentModel;
import com.lima.api.soccer.adapter.output.database.model.PlayerModel;
import com.lima.api.soccer.adapter.output.database.repository.payment.PaymentRepository;
import com.lima.api.soccer.application.entity.Payment;
import com.lima.api.soccer.application.port.payment.output.DisapprovePaymentOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DisapprovePaymentPersistence implements DisapprovePaymentOutputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisapprovePaymentPersistence.class);

    private final PaymentRepository paymentRepository;

    public DisapprovePaymentPersistence(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void updateDisapprovePayment(Payment payment) {

        LOGGER.info(String.format("Start update payment code=s%", payment.getCode()));
        PlayerModel playerModel = new PlayerModel(payment.getPlayer().getId(), payment.getPlayer().getCode(), payment.getPlayer().getName(), payment.getPlayer().getDob(), payment.getPlayer().getRg());
        PaymentModel paymentModel = new PaymentModel(payment.getId(), payment.getCode(), payment.getDatePayment(), playerModel, payment.getTypePaymentIndicator(), payment.getStatusPaymentIndicator());
        paymentRepository.save(paymentModel);
    }
}
