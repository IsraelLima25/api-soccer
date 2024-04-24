package com.lima.api.soccer.application.usecase.payment;

import com.lima.api.soccer.application.config.UseCase;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.entity.Payment;
import com.lima.api.soccer.application.entity.Player;
import com.lima.api.soccer.application.exception.BusinessExeption;
import com.lima.api.soccer.application.exception.PaymentNotExistsException;
import com.lima.api.soccer.application.port.payment.input.ApprovePaymentInputPort;
import com.lima.api.soccer.application.port.payment.input.FindPaymentByCodeInputPort;
import com.lima.api.soccer.application.port.payment.output.ApprovePaymentOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UseCase
public class ApprovePaymentUseCase implements ApprovePaymentInputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApprovePaymentUseCase.class);

    private final FindPaymentByCodeInputPort findPaymentByCodeInputPort;
    private final ApprovePaymentOutputPort approvePaymentOutputPort;

    public ApprovePaymentUseCase(ApprovePaymentOutputPort approvePaymentOutputPort,
                                 FindPaymentByCodeInputPort findPaymentByCodeInputPort) {
        this.approvePaymentOutputPort = approvePaymentOutputPort;
        this.findPaymentByCodeInputPort = findPaymentByCodeInputPort;
    }

    @Override
    public PaymentResponseDTO execute(String codePayment) throws PaymentNotExistsException, BusinessExeption {

        LOGGER.info(String.format("Start use case approve payment code=%s", codePayment));
        PaymentResponseDTO paymentResponseDTO = findPaymentByCodeInputPort.execute(codePayment);
        Payment payment = new Payment(paymentResponseDTO.id(), paymentResponseDTO.code(), paymentResponseDTO.datePayment(),
                new Player(paymentResponseDTO.playerResponseDTO().id(), paymentResponseDTO.playerResponseDTO().code(), paymentResponseDTO.playerResponseDTO().name(),
                        paymentResponseDTO.playerResponseDTO().dob(), paymentResponseDTO.playerResponseDTO().rg()), paymentResponseDTO.typePayment(), paymentResponseDTO.statusPaymentIndicator());
        payment.approve();
        LOGGER.info(String.format("Rule business approve payment code=%s execute succes", codePayment));
        approvePaymentOutputPort.updateApprovePayment(payment);

        return payment.toResponseDTO();
    }
}
