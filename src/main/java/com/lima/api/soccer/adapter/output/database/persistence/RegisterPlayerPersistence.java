package com.lima.api.soccer.adapter.output.database.persistence;

import com.lima.api.soccer.adapter.output.database.model.PlayerModel;
import com.lima.api.soccer.adapter.output.database.repository.player.PlayerRepository;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.entity.Player;
import com.lima.api.soccer.application.port.player.output.RegisterPlayerOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RegisterPlayerPersistence implements RegisterPlayerOutputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPlayerPersistence.class);

    private final PlayerRepository playerRepository;

    public RegisterPlayerPersistence(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerResponseDTO register(Player player) {
        LOGGER.info("Start transaction persist player database");
        PlayerModel playerModel = new PlayerModel(player.getName(), player.getDob(), player.getRg());
        playerRepository.save(playerModel);
        LOGGER.info("Finish transaction persist player database in success");
        return playerModel.toPlayerResponseDTO();
    }
}
