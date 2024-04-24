package com.lima.api.soccer.adapter.output.database.persistence;

import com.lima.api.soccer.adapter.output.database.model.PlayerModel;
import com.lima.api.soccer.adapter.output.database.repository.player.PlayerRepository;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.port.player.output.FindPlayerByCodeOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindPlayerByCodePersistence implements FindPlayerByCodeOutputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindPlayerByCodePersistence.class);

    private final PlayerRepository playerRepository;

    public FindPlayerByCodePersistence(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<PlayerResponseDTO> findPlayerById(String code) {
        LOGGER.info("Start find player in DB");
        Optional<PlayerModel> optionalPlayerModel = playerRepository.findByCode(code);
        return optionalPlayerModel.map(PlayerModel::toPlayerResponseDTO);
    }
}
