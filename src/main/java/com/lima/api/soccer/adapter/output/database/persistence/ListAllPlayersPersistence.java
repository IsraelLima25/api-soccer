package com.lima.api.soccer.adapter.output.database.persistence;

import com.lima.api.soccer.adapter.output.database.model.PlayerModel;
import com.lima.api.soccer.adapter.output.database.repository.player.PlayerRepository;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.port.player.output.ListAllPlayersOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListAllPlayersPersistence implements ListAllPlayersOutputPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListAllPlayersPersistence.class);
    private final PlayerRepository playerRepository;

    public ListAllPlayersPersistence(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<PlayerResponseDTO> listAll() {
        LOGGER.info("Start find all players in DB");
        List<PlayerModel> allPlayersModel = playerRepository.findAll();
        LOGGER.info("List players load");
        return allPlayersModel.stream().map(PlayerModel::toPlayerResponseDTO).toList();
    }
}
