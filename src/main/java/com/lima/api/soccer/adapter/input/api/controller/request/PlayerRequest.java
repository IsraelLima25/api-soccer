package com.lima.api.soccer.adapter.input.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lima.api.soccer.application.dto.player.request.PlayerResquestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PlayerRequest(
        @NotBlank String name,
        @NotNull @JsonProperty("dataNascimento") LocalDate dob,
        @NotBlank String rg) {

    public PlayerResquestDTO toPlayerRequestDTO(){
        return new PlayerResquestDTO(name, dob, rg);
    }
}
