package com.lima.api.soccer.application.usecase.payment;

import com.lima.api.soccer.application.config.UseCase;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.exception.PaymentNotExistsException;
import com.lima.api.soccer.application.port.payment.input.FindPaymentByCodeInputPort;
import com.lima.api.soccer.application.port.payment.output.FindPaymentByCodeOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@UseCase
public class FindPaymentByCodeUseCase implements FindPaymentByCodeInputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindPaymentByCodeUseCase.class);

    private final FindPaymentByCodeOutputPort findPaymentByCodeOutputPort;

    public FindPaymentByCodeUseCase(FindPaymentByCodeOutputPort findPaymentByCodeOutputPort) {
        this.findPaymentByCodeOutputPort = findPaymentByCodeOutputPort;
    }

    @Override
    public PaymentResponseDTO execute(String codePayment) {
        LOGGER.info(String.format("Start use case find payment by code=%s", codePayment));
        Optional<PaymentResponseDTO> optionalPaymentResponseDTO = findPaymentByCodeOutputPort.findPaymentByCode(codePayment);
        if(optionalPaymentResponseDTO.isEmpty()){
            LOGGER.error(String.format("Use case not exists code=%s", codePayment));
            throw new PaymentNotExistsException("This payment not exists");
        }
        LOGGER.info(String.format("Payment code=%s find success", codePayment));
        return optionalPaymentResponseDTO.get();
    }
}
