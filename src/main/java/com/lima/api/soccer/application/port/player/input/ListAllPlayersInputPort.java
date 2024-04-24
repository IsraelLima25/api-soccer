package com.lima.api.soccer.application.port.player.input;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;

import java.util.List;

public interface ListAllPlayersInputPort {

    List<PlayerResponseDTO> execute();
}
