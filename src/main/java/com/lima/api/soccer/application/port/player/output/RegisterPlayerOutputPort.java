package com.lima.api.soccer.application.port.player.output;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.entity.Player;

public interface RegisterPlayerOutputPort {
    PlayerResponseDTO register(Player player);
}
