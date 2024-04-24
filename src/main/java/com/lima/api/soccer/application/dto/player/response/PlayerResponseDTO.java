package com.lima.api.soccer.application.dto.player.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record PlayerResponseDTO(Long id, String code, String name, @JsonProperty("dataNascimento") LocalDate dob, String rg) { }
