package com.lima.api.soccer.application.entity;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;

import java.time.LocalDate;

public class Player {

    private Long id;
    private String code;
    private String name;
    private LocalDate dob;
    private String rg;

    public Player(Long id, String code, String name, LocalDate dob, String rg){
        this.id = id;
        this.code = code;
        this.name = name;
        this.dob = dob;
        this.rg = rg;
    }

    public Player(String name, LocalDate dob, String rg){
        this.name = name;
        this.dob = dob;
        this.rg = rg;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getRg() {
        return rg;
    }

    public String getCode() {
        return code;
    }

    public PlayerResponseDTO toResponseDTO(){
        return new PlayerResponseDTO(id, code, name, dob, rg);
    }
}
