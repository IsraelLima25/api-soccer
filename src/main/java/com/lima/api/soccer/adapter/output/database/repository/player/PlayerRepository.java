package com.lima.api.soccer.adapter.output.database.repository.player;

import com.lima.api.soccer.adapter.output.database.model.PlayerModel;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    Optional<PlayerModel> findByCode(String code);
    List<PlayerModel> findAll();
    PlayerModel save(PlayerModel playerModel);
}
