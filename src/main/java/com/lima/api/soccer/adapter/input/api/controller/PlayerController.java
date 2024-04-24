package com.lima.api.soccer.adapter.input.api.controller;

import com.lima.api.soccer.adapter.doc.PlayerSwagger;
import com.lima.api.soccer.adapter.exception.handler.ResourceNotFoundException;
import com.lima.api.soccer.adapter.input.api.controller.request.PlayerRequest;
import com.lima.api.soccer.adapter.input.api.controller.response.PlayerRepresentationDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.exception.PlayerNotExistsException;
import com.lima.api.soccer.application.port.player.input.FindPlayerByCodeInputPort;
import com.lima.api.soccer.application.port.player.input.ListAllPlayersInputPort;
import com.lima.api.soccer.application.port.player.input.RegisterPlayerInputPort;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/players", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController implements PlayerSwagger {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    private final RegisterPlayerInputPort registerPlayerInputPort;

    private final ListAllPlayersInputPort listAllPlayersInputPort;

    private final FindPlayerByCodeInputPort findPlayerByCodeInputPort;

    public PlayerController(RegisterPlayerInputPort registerPlayerInputPort, ListAllPlayersInputPort listAllPlayersInputPort, FindPlayerByCodeInputPort findPlayerByCodeInputPort) {
        this.registerPlayerInputPort = registerPlayerInputPort;
        this.listAllPlayersInputPort = listAllPlayersInputPort;
        this.findPlayerByCodeInputPort = findPlayerByCodeInputPort;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<PlayerRepresentationDTO> register(@Valid @RequestBody PlayerRequest request, UriComponentsBuilder uriBuilder) {

        //TODO check transactional propagation
        LOGGER.info(String.format("Received request to register player name=%s", request.name()));
        PlayerResponseDTO playerResponseDTO = registerPlayerInputPort.execute(request.toPlayerRequestDTO());
        PlayerRepresentationDTO playerRepresentationDTO = new PlayerRepresentationDTO(playerResponseDTO.code(), playerResponseDTO.name(), playerResponseDTO.dob(), playerResponseDTO.rg());
        URI uri = uriBuilder.path("/v1/players/{id}").buildAndExpand(playerRepresentationDTO.code()).toUri();
        LOGGER.info(String.format("Player code=%s registry in success", playerRepresentationDTO.code()));
        return ResponseEntity.created(uri).body(playerRepresentationDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PlayerRepresentationDTO>> listAll() {

        LOGGER.info("Received request list all players");
        List<PlayerResponseDTO> listAllPlayersResponseDTO = listAllPlayersInputPort.execute();
        List<PlayerRepresentationDTO> listPlayers = listAllPlayersResponseDTO.stream().map(item ->
                new PlayerRepresentationDTO(item.code(), item.name(), item.dob(), item.rg())).toList();
        LOGGER.info("Finish request list all players");
        return ResponseEntity.ok(listPlayers);
    }

    @Override
    @GetMapping(value = "/{code}")
    public ResponseEntity<PlayerRepresentationDTO> findByCode(@PathVariable("code") String code) {

        try{
            LOGGER.info(String.format("Received request to find player by code=%s", code));
            PlayerResponseDTO playerResponseDTO = findPlayerByCodeInputPort.execute(code);
            PlayerRepresentationDTO playerRepresentationDTO = new PlayerRepresentationDTO(playerResponseDTO.code(), playerResponseDTO.name(), playerResponseDTO.dob(), playerResponseDTO.rg());
            LOGGER.info(String.format("Player code=%s find success", code));
            return ResponseEntity.ok(playerRepresentationDTO);
        }catch (PlayerNotExistsException ex){
            LOGGER.error(String.format("Player code=%s not found", code));
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }
}
