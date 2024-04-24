package com.lima.api.soccer.application.usecase.player;

import com.lima.api.soccer.application.config.UseCase;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.port.player.input.ListAllPlayersInputPort;
import com.lima.api.soccer.application.port.player.output.ListAllPlayersOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@UseCase
public class ListAllPlayersUseCase implements ListAllPlayersInputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListAllPlayersUseCase.class);

    private final ListAllPlayersOutputPort listAllPlayersOutputPort;

    public ListAllPlayersUseCase(ListAllPlayersOutputPort listAllPlayersOutputPort) {
        this.listAllPlayersOutputPort = listAllPlayersOutputPort;
    }

    @Override
    public List<PlayerResponseDTO> execute() {
        LOGGER.info("Start use case list all players");
        return listAllPlayersOutputPort.listAll();
    }

}
