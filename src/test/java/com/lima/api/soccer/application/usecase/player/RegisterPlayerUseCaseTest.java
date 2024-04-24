package com.lima.api.soccer.application.usecase.player;

import com.lima.api.soccer.application.dto.player.request.PlayerResquestDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.entity.Player;
import com.lima.api.soccer.application.port.player.output.RegisterPlayerOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RegisterPlayerUseCaseTest {

    @InjectMocks
    RegisterPlayerUseCase registerPlayerUseCase;
    @Mock
    RegisterPlayerOutputPort registerPlayerOutputPort;

    @Test
    void shouldRegisterPlayerWhenUseCaseCall(){

        PlayerResquestDTO playerResquestDTO = new PlayerResquestDTO("Player 1", LocalDate.now(), "14548547");
        Mockito.when(registerPlayerOutputPort.register(Mockito.any(Player.class))).thenReturn(new PlayerResponseDTO(1L, UUID.randomUUID().toString(),"Player 1", LocalDate.now(), "14548547"));
        PlayerResponseDTO response = registerPlayerUseCase.execute(playerResquestDTO);

        assertEquals("Player 1", response.name());
        assertEquals(LocalDate.now(), response.dob());
        assertEquals("14548547", response.rg());
    }
}