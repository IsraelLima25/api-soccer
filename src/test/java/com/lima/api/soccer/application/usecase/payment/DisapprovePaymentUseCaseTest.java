package com.lima.api.soccer.application.usecase.payment;

import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.exception.BusinessExeption;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;
import com.lima.api.soccer.application.port.payment.input.FindPaymentByCodeInputPort;
import com.lima.api.soccer.application.port.payment.output.DisapprovePaymentOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DisapprovePaymentUseCaseTest {

    @InjectMocks
    DisapprovePaymentUseCase disapprovePaymentUseCase;
    @Mock
    DisapprovePaymentOutputPort disapprovePaymentOutputPort;
    @Mock
    FindPaymentByCodeInputPort findPaymentByCodeInputPort;

    @Test
    void shouldDisapprovePaymentWhenPaymentNotDisapprove(){

        when(findPaymentByCodeInputPort.execute(any())).thenReturn(new PaymentResponseDTO(1L, UUID.randomUUID().toString(), LocalDate.now(),
                new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "142587"), TypePaymentIndicator.PIX, StatusPaymentIndicator.PENDING));

        PaymentResponseDTO paymentResponseDTO = disapprovePaymentUseCase.execute(UUID.randomUUID().toString());

        assertEquals(StatusPaymentIndicator.DISAPPROVE, paymentResponseDTO.statusPaymentIndicator());
        verify(disapprovePaymentOutputPort, times(1)).updateDisapprovePayment(any());
    }

    @Test
    void shouldThrowBusinessExceptionWhenPaymentAlreadyDisapprove(){

        when(findPaymentByCodeInputPort.execute(any())).thenReturn(new PaymentResponseDTO(1L, UUID.randomUUID().toString(), LocalDate.now(),
                new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "142587"), TypePaymentIndicator.PIX, StatusPaymentIndicator.DISAPPROVE));

        assertThrows(BusinessExeption.class, () -> {
            disapprovePaymentUseCase.execute(UUID.randomUUID().toString());
        });
    }
}