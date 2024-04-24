package com.lima.api.soccer.application.usecase.player;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.exception.PlayerNotExistsException;
import com.lima.api.soccer.application.port.player.output.FindPlayerByCodeOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FindPlayerByCodeUseCaseTest {

    @InjectMocks
    FindPlayerByCodeUseCase findPlayerByCodeUseCase;

    @Mock
    FindPlayerByCodeOutputPort findPlayerByCodeOutputPort;

    @Test
    void shouldReturnPlayerWhenIdExists(){

        when(findPlayerByCodeOutputPort.findPlayerById(any())).thenReturn(Optional.of(new PlayerResponseDTO(1L,UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "1425987")));
        PlayerResponseDTO playerResponseDTO = findPlayerByCodeUseCase.execute(UUID.randomUUID().toString());

        assertEquals("Player 1", playerResponseDTO.name());
        assertEquals(LocalDate.now(), playerResponseDTO.dob());
        assertEquals("1425987", playerResponseDTO.rg());
    }

    @Test
    void shouldThrowNotExistsPlayerExceptionWhenPlayerNotExists(){

        when(findPlayerByCodeOutputPort.findPlayerById(any())).thenThrow(PlayerNotExistsException.class);
        assertThrows(PlayerNotExistsException.class, () -> {
           findPlayerByCodeUseCase.execute(UUID.randomUUID().toString());
        });
    }
}