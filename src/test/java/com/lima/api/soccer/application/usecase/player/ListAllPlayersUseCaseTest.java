package com.lima.api.soccer.application.usecase.player;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.port.player.output.ListAllPlayersOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ListAllPlayersUseCaseTest {

    @InjectMocks
    ListAllPlayersUseCase listAllPlayersUseCase;
    @Mock
    ListAllPlayersOutputPort listAllPlayersOutputPort;

    @Test
    void shouldListAllPlayersWhenListAllUseCaseCall(){

        Mockito.when(listAllPlayersOutputPort.listAll()).thenReturn(List.of(new PlayerResponseDTO(1L, UUID.randomUUID().toString(),"Player 1", LocalDate.now(), "1425475")));
        List<PlayerResponseDTO> listPlayers = listAllPlayersUseCase.execute();

        assertEquals(1, listPlayers.size());
        assertEquals("Player 1", listPlayers.get(0).name());
        assertEquals(LocalDate.now(), listPlayers.get(0).dob());
        assertEquals("1425475", listPlayers.get(0).rg());
    }
}