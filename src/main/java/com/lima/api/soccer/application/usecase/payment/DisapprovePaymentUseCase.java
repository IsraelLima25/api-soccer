package com.lima.api.soccer.application.usecase.payment;

import com.lima.api.soccer.application.config.UseCase;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.entity.Payment;
import com.lima.api.soccer.application.entity.Player;
import com.lima.api.soccer.application.port.payment.input.DisapprovePaymentInputPort;
import com.lima.api.soccer.application.port.payment.input.FindPaymentByCodeInputPort;
import com.lima.api.soccer.application.port.payment.output.DisapprovePaymentOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UseCase
public class DisapprovePaymentUseCase implements DisapprovePaymentInputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisapprovePaymentUseCase.class);
    private final FindPaymentByCodeInputPort findPaymentByCodeInputPort;
    private final DisapprovePaymentOutputPort disapprovePaymentOutputPort;

    public DisapprovePaymentUseCase(FindPaymentByCodeInputPort findPaymentByCodeInputPort, DisapprovePaymentOutputPort disapprovePaymentOutputPort) {
        this.findPaymentByCodeInputPort = findPaymentByCodeInputPort;
        this.disapprovePaymentOutputPort = disapprovePaymentOutputPort;
    }

    @Override
    public PaymentResponseDTO execute(String codePayment) {

        LOGGER.info(String.format("Start use case disapprove payment code=%s", codePayment));
        PaymentResponseDTO paymentResponseDTO = findPaymentByCodeInputPort.execute(codePayment);
        Payment payment = new Payment(paymentResponseDTO.id(), paymentResponseDTO.code(), paymentResponseDTO.datePayment(),
                new Player(paymentResponseDTO.playerResponseDTO().id(), paymentResponseDTO.playerResponseDTO().code(), paymentResponseDTO.playerResponseDTO().name(),
                        paymentResponseDTO.playerResponseDTO().dob(), paymentResponseDTO.playerResponseDTO().rg()), paymentResponseDTO.typePayment(), paymentResponseDTO.statusPaymentIndicator());
        payment.disapprove();
        LOGGER.info(String.format("Rule business disapprove payment code=%s execute succes", codePayment));
        disapprovePaymentOutputPort.updateDisapprovePayment(payment);

        return payment.toResponseDTO();
    }
}
