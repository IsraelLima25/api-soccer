package com.lima.api.soccer.application.port.player.output;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;

import java.util.List;

public interface ListAllPlayersOutputPort {

    List<PlayerResponseDTO> listAll();
}
