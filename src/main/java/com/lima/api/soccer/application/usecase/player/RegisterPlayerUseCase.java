package com.lima.api.soccer.application.usecase.player;

import com.lima.api.soccer.application.config.UseCase;
import com.lima.api.soccer.application.dto.player.request.PlayerResquestDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lima.api.soccer.application.port.player.input.RegisterPlayerInputPort;
import com.lima.api.soccer.application.port.player.output.RegisterPlayerOutputPort;

@UseCase
public class RegisterPlayerUseCase implements RegisterPlayerInputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPlayerUseCase.class);

    private final RegisterPlayerOutputPort registerPlayerOutputPort;

    public RegisterPlayerUseCase(RegisterPlayerOutputPort registerPlayerOutputPort) {
        this.registerPlayerOutputPort = registerPlayerOutputPort;
    }

    @Override
    public PlayerResponseDTO execute(PlayerResquestDTO playerResquestDTO) {
        LOGGER.info("Start use case register player");
        Player player = new Player(playerResquestDTO.name(), playerResquestDTO.dob(), playerResquestDTO.rg());
        LOGGER.info("Finish use case register player in success");
        return registerPlayerOutputPort.register(player);
    }
}
