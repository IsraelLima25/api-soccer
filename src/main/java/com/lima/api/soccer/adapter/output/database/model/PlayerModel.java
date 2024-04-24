package com.lima.api.soccer.adapter.output.database.model;

import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tbl_player")
public class PlayerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "date_birthday")
    private LocalDate dob;

    @Column(name = "rg")
    private String rg;

    @Deprecated
    public PlayerModel() { }

    public PlayerModel(Long id, String code, String name, LocalDate dob, String rg) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dob = dob;
        this.rg = rg;
    }

    public PlayerModel(String name, LocalDate dob, String rg) {
        this.name = name;
        this.dob = dob;
        this.rg = rg;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
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

    public PlayerResponseDTO toPlayerResponseDTO() {
        return new PlayerResponseDTO(id, code, name, dob, rg);
    }

    @PrePersist
    private void loadingCode() {
        this.code = UUID.randomUUID().toString();
    }
}
