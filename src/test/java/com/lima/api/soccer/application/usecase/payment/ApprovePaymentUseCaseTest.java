package com.lima.api.soccer.application.usecase.payment;

import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.exception.BusinessExeption;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;
import com.lima.api.soccer.application.port.payment.input.FindPaymentByCodeInputPort;
import com.lima.api.soccer.application.port.payment.output.ApprovePaymentOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ApprovePaymentUseCaseTest {

    @InjectMocks
    ApprovePaymentUseCase approvePaymentUseCase;
    @Mock
    FindPaymentByCodeInputPort findPaymentByCodeInputPort;
    @Mock
    ApprovePaymentOutputPort approvePaymentOutputPort;

    @Test
    void shouldApprovePaymentWhenPaymentNotApprove(){
        when(findPaymentByCodeInputPort.execute(any())).thenReturn(new PaymentResponseDTO(1L, UUID.randomUUID().toString(), LocalDate.now(),
                new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "142587"), TypePaymentIndicator.PIX, StatusPaymentIndicator.PENDING));

        PaymentResponseDTO paymentResponseDTO = approvePaymentUseCase.execute(UUID.randomUUID().toString());

        assertEquals(StatusPaymentIndicator.APPROVE, paymentResponseDTO.statusPaymentIndicator());
        verify(approvePaymentOutputPort, times(1)).updateApprovePayment(any());
    }

    @Test
    void shouldThrowBusinessExceptionWhenAppovePaymentAlreadyApprove(){

        when(findPaymentByCodeInputPort.execute(any())).thenReturn(new PaymentResponseDTO(1L, UUID.randomUUID().toString(), LocalDate.now(),
                new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "142587"), TypePaymentIndicator.PIX, StatusPaymentIndicator.APPROVE));

        assertThrows(BusinessExeption.class, () -> {
            approvePaymentUseCase.execute(UUID.randomUUID().toString());
        });
    }
}