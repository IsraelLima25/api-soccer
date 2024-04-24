package com.lima.api.soccer.adapter.input.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lima.api.soccer.adapter.input.api.controller.request.PlayerRequest;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.port.player.input.FindPlayerByCodeInputPort;
import com.lima.api.soccer.application.port.player.input.ListAllPlayersInputPort;
import com.lima.api.soccer.application.port.player.input.RegisterPlayerInputPort;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
@ContextConfiguration(classes = { PlayerController.class })
class PlayerControllerTest {

    private static final String URI = "/v1/players";

    private static ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegisterPlayerInputPort registerPlayerInputPort;

    @MockBean
    private ListAllPlayersInputPort listAllPlayersInputPort;

    @MockBean
    private FindPlayerByCodeInputPort findPlayerByCodeInputPort;

    private PlayerRequest playerRequestValid;

    @BeforeAll
    static void setupBeforeClass() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void setupInitial() {
        playerRequestValid = new PlayerRequest("Player 1", LocalDate.now(), "14546589");
    }

    @AfterEach
    void setupFinal() {	}

    @DisplayName("Should return status 201 and register player when send valid request")
    @Test
    void shouldReturnStatus201WhenSendValidRequest() throws Exception {

        String code = UUID.randomUUID().toString();

        when(registerPlayerInputPort.execute(any())).thenReturn(new PlayerResponseDTO(1L, code, "Player 1", LocalDate.now(), "145487"));

        mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(playerRequestValid)))
                .andDo(print())
                .andExpect(jsonPath("$.code", is(code)))
                .andExpect(jsonPath("$.name", is("Player 1")))
                .andExpect(jsonPath("$.dateOfBirthday", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.rg", is("145487")))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @DisplayName("Should return status 400 when field name is blank")
    @Test
    void shoudlReturnStatus400WhenFieldNomeIsNull() throws Exception {

        PlayerRequest playerRequest = new PlayerRequest("", LocalDate.now(), "145487854");

        mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(playerRequest)))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Should return status 200 when list all players")
    @Test
    void shoudlReturnStatus200AndListPlayersWhenListAllPlayers() throws Exception {

        when(listAllPlayersInputPort.execute()).thenReturn(List.of(
                new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "145487"),
                new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 2", LocalDate.now(), "145489")));

        mockMvc.perform(MockMvcRequestBuilders.get(URI).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @DisplayName("Should return status 200 and return player when find player by code")
    @Test
    void shoudlReturnStatus200AndReturnPlayerWhenFindPlayerByCode() throws Exception {

        String code = UUID.randomUUID().toString();

        when(findPlayerByCodeInputPort.execute(code)).thenReturn(new PlayerResponseDTO(1L, UUID.randomUUID().toString(), "Player 1", LocalDate.now(), "145487"));

        mockMvc.perform(MockMvcRequestBuilders.get(URI.concat("/{code}"), code).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Player 1")))
                .andExpect(jsonPath("$.rg", is("145487")));
    }
}