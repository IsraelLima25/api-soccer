package com.lima.api.soccer.application.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    //TODO Usar lib asserj

    @Test
    void shouldCreatePlayerWhenPlayerValid(){
        Player player = new Player("Player1", LocalDate.now(), "156545782");

        assertEquals("Player1", player.getName());
        assertEquals(LocalDate.now(), player.getDob());
        assertEquals("156545782", player.getRg());
    }
}