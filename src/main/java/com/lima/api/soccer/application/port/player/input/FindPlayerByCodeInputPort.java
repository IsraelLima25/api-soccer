package com.lima.api.soccer.application.port.player.input;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;

public interface FindPlayerByCodeInputPort {

    PlayerResponseDTO execute (String codePlayer);
}
