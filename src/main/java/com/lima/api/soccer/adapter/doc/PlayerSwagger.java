package com.lima.api.soccer.adapter.doc;

import com.lima.api.soccer.adapter.exception.handler.response.ApiErrorResponse;
import com.lima.api.soccer.adapter.input.api.controller.request.PlayerRequest;
import com.lima.api.soccer.adapter.input.api.controller.response.PaymentRepresentationDTO;
import com.lima.api.soccer.adapter.input.api.controller.response.PlayerRepresentationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Tag(name = "Player", description = "")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentRepresentationDTO.class))
        }),
        @ApiResponse(responseCode = "200", description = "Ok", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentRepresentationDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not-Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        }),
})
public interface PlayerSwagger {

    @Operation(operationId = "registerPlayer", summary = "Endpoint to regystry player", description = "")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
    ResponseEntity<PlayerRepresentationDTO> register(PlayerRequest request, UriComponentsBuilder uriBuilder);

    @Operation(operationId = "listAllPlayers", summary = "Endpoint to list all players", description = "")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok")})
    ResponseEntity<List<PlayerRepresentationDTO>> listAll();

    @GetMapping
    @Operation(operationId = "findPlayerByCode", summary = "Endpoint find player by code", description = "")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok")})
    ResponseEntity<PlayerRepresentationDTO> findByCode(@PathVariable("code") String code);

}
