package com.lima.api.soccer.adapter.input.api.controller.response;

import java.time.LocalDate;

public record PlayerRepresentationDTO(
        String code,
        String name,
        LocalDate dateOfBirthday,
        String rg
) { }
