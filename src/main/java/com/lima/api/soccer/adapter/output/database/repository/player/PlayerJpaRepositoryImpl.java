package com.lima.api.soccer.adapter.output.database.repository.player;

import com.lima.api.soccer.adapter.output.database.JpaRepository.PlayerJPARepository;
import com.lima.api.soccer.adapter.output.database.model.PlayerModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlayerJpaRepositoryImpl implements PlayerRepository{

    private final PlayerJPARepository playerJPARepository;

    public PlayerJpaRepositoryImpl(PlayerJPARepository playerJPARepository) {
        this.playerJPARepository = playerJPARepository;
    }

    @Override
    public Optional<PlayerModel> findByCode(String code) {
        return playerJPARepository.findByCode(code);
    }

    @Override
    public List<PlayerModel> findAll() {
        return playerJPARepository.findAll();
    }

    @Override
    public PlayerModel save(PlayerModel playerModel) {
        return playerJPARepository.save(playerModel);
    }
}
