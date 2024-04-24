package com.lima.api.soccer.application.usecase.payment;

import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.exception.PaymentNotExistsException;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;
import com.lima.api.soccer.application.port.payment.output.FindPaymentByCodeOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FindPaymentByCodeUseCaseTest {

    @InjectMocks
    FindPaymentByCodeUseCase findPaymentByCodeUseCase;

    @Mock
    FindPaymentByCodeOutputPort findPaymentByCodeOutputPort;

    @Test
    void shouldReturnPaymentWhenIdExists(){

        when(findPaymentByCodeOutputPort.findPaymentByCode(any())).thenReturn(Optional.of(new PaymentResponseDTO(1L, UUID.randomUUID().toString(), LocalDate.now(),
                new PlayerResponseDTO(1L, UUID.randomUUID().toString(),"Player 1", LocalDate.now(), "142587"), TypePaymentIndicator.PIX, StatusPaymentIndicator.PENDING)));
        PaymentResponseDTO paymentResponseDTO = findPaymentByCodeUseCase.execute(UUID.randomUUID().toString());

        assertEquals(TypePaymentIndicator.PIX, paymentResponseDTO.typePayment());
    }

    @Test
    void shouldThrowNotExistsPaymentExceptionWhenPaymentNotExists(){

        when(findPaymentByCodeOutputPort.findPaymentByCode(any())).thenThrow(PaymentNotExistsException.class);
        assertThrows(PaymentNotExistsException.class, () -> {
            findPaymentByCodeUseCase.execute(UUID.randomUUID().toString());
        });
    }

}