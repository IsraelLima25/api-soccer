package com.lima.api.soccer.adapter.output.database.JpaRepository;

import com.lima.api.soccer.adapter.output.database.model.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerJPARepository extends JpaRepository<PlayerModel, Long> {

    Optional<PlayerModel> findByCode(String code);

}
