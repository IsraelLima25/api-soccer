package com.lima.api.soccer.application.dto.player.request;

import java.time.LocalDate;

public  record PlayerResquestDTO(String name, LocalDate dob, String rg) { }