package com.lima.api.soccer.application.usecase.payment;

import com.lima.api.soccer.application.config.UseCase;
import com.lima.api.soccer.application.dto.payment.request.PaymentRequestDTO;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.entity.Payment;
import com.lima.api.soccer.application.entity.Player;
import com.lima.api.soccer.application.exception.PlayerNotExistsException;
import com.lima.api.soccer.application.port.payment.input.RegisterPaymentInputPort;
import com.lima.api.soccer.application.port.payment.output.RegisterPaymentOutputPort;
import com.lima.api.soccer.application.port.player.input.FindPlayerByCodeInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UseCase
public class RegisterPaymentUseCase implements RegisterPaymentInputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPaymentUseCase.class);

    private final RegisterPaymentOutputPort registerPaymentOutputPort;

    private final FindPlayerByCodeInputPort findPlayerByCodeInputPort;

    public RegisterPaymentUseCase(RegisterPaymentOutputPort registerPaymentOutputPort, FindPlayerByCodeInputPort findPlayerByCodeInputPort) {
        this.registerPaymentOutputPort = registerPaymentOutputPort;
        this.findPlayerByCodeInputPort = findPlayerByCodeInputPort;
    }

    @Override
    public PaymentResponseDTO execute(PaymentRequestDTO paymentRequestDTO) throws PlayerNotExistsException {

        LOGGER.info(String.format("Start use case to register payment to player code=%s", paymentRequestDTO.codePlayer()));

        LOGGER.info(String.format("Find player code=%s", paymentRequestDTO.codePlayer()));
        PlayerResponseDTO playerResponseDTO = findPlayerByCodeInputPort.execute(paymentRequestDTO.codePlayer());
        LOGGER.info(String.format("Player code=%s find success", paymentRequestDTO.codePlayer()));

        LOGGER.info("Start register payment");
        //TODO apply pattern factory obj player and payment
        Player player = new Player(playerResponseDTO.id(), playerResponseDTO.code(), playerResponseDTO.name(), playerResponseDTO.dob(), playerResponseDTO.rg());
        Payment payment = new Payment(paymentRequestDTO.datePayment(), player, paymentRequestDTO.typePayment());
        return registerPaymentOutputPort.registerPayment(payment);

    }
}
