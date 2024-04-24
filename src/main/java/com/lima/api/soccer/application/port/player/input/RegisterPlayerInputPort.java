package com.lima.api.soccer.application.port.player.input;

import com.lima.api.soccer.application.dto.player.request.PlayerResquestDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;

public interface RegisterPlayerInputPort {

    PlayerResponseDTO execute(PlayerResquestDTO playerResquestDTO);
}
