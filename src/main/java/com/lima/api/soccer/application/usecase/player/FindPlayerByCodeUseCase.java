package com.lima.api.soccer.application.usecase.player;

import com.lima.api.soccer.adapter.input.api.controller.PlayerController;
import com.lima.api.soccer.application.config.UseCase;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.exception.PlayerNotExistsException;
import com.lima.api.soccer.application.port.player.input.FindPlayerByCodeInputPort;
import com.lima.api.soccer.application.port.player.output.FindPlayerByCodeOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@UseCase
public class FindPlayerByCodeUseCase implements FindPlayerByCodeInputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindPlayerByCodeUseCase.class);

    private final FindPlayerByCodeOutputPort findPlayerByCodeOutputPort;

    public FindPlayerByCodeUseCase(FindPlayerByCodeOutputPort findPlayerByCodeOutputPort) {
        this.findPlayerByCodeOutputPort = findPlayerByCodeOutputPort;
    }

    @Override
    public PlayerResponseDTO execute(String codePlayer) {

        //LOGGER.info(String.format("Start use case find to player code=s%", codePlayer));
        Optional<PlayerResponseDTO> optionalPlayerResponseDTO = findPlayerByCodeOutputPort.findPlayerById(codePlayer);
        if(optionalPlayerResponseDTO.isEmpty()){
            LOGGER.error(String.format("Player code=s% not exists", codePlayer));
            throw new PlayerNotExistsException("This player not exists");
        }
        return optionalPlayerResponseDTO.get();
    }
}
