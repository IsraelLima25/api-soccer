package com.lima.api.soccer.adapter.doc;

import com.lima.api.soccer.adapter.exception.handler.response.ApiErrorResponse;
import com.lima.api.soccer.adapter.input.api.controller.request.PaymentRequest;
import com.lima.api.soccer.adapter.input.api.controller.response.PaymentRepresentationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Payment", description = "")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentRepresentationDTO.class))
        }),
        @ApiResponse(responseCode = "200", description = "Ok", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentRepresentationDTO.class))
        }),
        @ApiResponse(responseCode = "204", description = "No-Content", content = {
                @Content(mediaType = "application/json")
        }),
        @ApiResponse(responseCode = "400", description = "Bad", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not-Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        }),
})
public interface PaymentSwagger {

    @Operation(operationId = "registerPayment", summary = "Endpoint to regystry payment", description = "")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
    ResponseEntity<PaymentRepresentationDTO> register(PaymentRequest request, UriComponentsBuilder uriBuilder);

    @Operation(operationId = "registerPayment", summary = "Endpoint find payment by code", description = "")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok")})
    ResponseEntity<PaymentRepresentationDTO> findByCode(@PathVariable("code") String code);

    @Operation(operationId = "approvePayment", summary = "Endpoint to approve payment", description = "")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No-Content")})
    ResponseEntity<Void> approve(@PathVariable("code") String code);

    @Operation(operationId = "disapprovePayment", summary = "Endpoint to disapprove payment", description = "")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No-Content")})
    ResponseEntity<Void> disapprove(@PathVariable("code") String code);

}
