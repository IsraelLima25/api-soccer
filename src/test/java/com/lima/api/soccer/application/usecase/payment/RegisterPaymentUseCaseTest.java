package com.lima.api.soccer.application.usecase.payment;

import com.lima.api.soccer.application.dto.payment.request.PaymentRequestDTO;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.exception.PlayerNotExistsException;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;
import com.lima.api.soccer.application.port.payment.output.RegisterPaymentOutputPort;
import com.lima.api.soccer.application.port.player.input.FindPlayerByCodeInputPort;
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
class RegisterPaymentUseCaseTest {

    @InjectMocks
    private RegisterPaymentUseCase registerPaymentUseCase;
    @Mock
    RegisterPaymentOutputPort registerPaymentOutputPort;
    @Mock
    FindPlayerByCodeInputPort findPlayerByCodeInputPort;

    @Test
    void shouldRegisterPaymentWhenUseCaseCall(){

        when(findPlayerByCodeInputPort.execute(any())).thenReturn(new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "1458745"));
        when(registerPaymentOutputPort.registerPayment(any())).thenReturn(new PaymentResponseDTO(1L, UUID.randomUUID().toString(), LocalDate.now(), new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "142587"),TypePaymentIndicator.PIX, StatusPaymentIndicator.PENDING));
        PaymentResponseDTO paymentResponseDTO = registerPaymentUseCase.execute(new PaymentRequestDTO(LocalDate.now(), UUID.randomUUID().toString(), TypePaymentIndicator.COIN));

        verify(registerPaymentOutputPort, times(1)).registerPayment(any());
    }

    @Test
    void shouldThrowNotExistsPaymentExceptionWhenPlayerNotExists(){
        when(findPlayerByCodeInputPort.execute(any())).thenThrow(PlayerNotExistsException.class);
        assertThrows(PlayerNotExistsException.class, () -> {
            registerPaymentUseCase.execute(new PaymentRequestDTO(LocalDate.now(), UUID.randomUUID().toString(), TypePaymentIndicator.COIN));
        });
        verifyNoInteractions(registerPaymentOutputPort);
    }
}