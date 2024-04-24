package com.lima.api.soccer.adapter.input.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lima.api.soccer.adapter.exception.handler.BusinessException;
import com.lima.api.soccer.adapter.exception.handler.ResourceExceptionHandler;
import com.lima.api.soccer.adapter.input.api.controller.request.PaymentRequest;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.exception.PaymentNotExistsException;
import com.lima.api.soccer.application.exception.PlayerNotExistsException;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;
import com.lima.api.soccer.application.port.payment.input.ApprovePaymentInputPort;
import com.lima.api.soccer.application.port.payment.input.DisapprovePaymentInputPort;
import com.lima.api.soccer.application.port.payment.input.FindPaymentByCodeInputPort;
import com.lima.api.soccer.application.port.payment.input.RegisterPaymentInputPort;
import com.lima.api.soccer.application.port.payment.output.RegisterPaymentOutputPort;
import com.lima.api.soccer.application.port.player.input.FindPlayerByCodeInputPort;
import com.lima.api.soccer.application.port.player.output.FindPlayerByCodeOutputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = { PaymentController.class, ResourceExceptionHandler.class})
public class PaymentControllerTest {

    private static final String URI = "/v1/payments";

    private static ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RegisterPaymentInputPort registerPaymentInputPort;
    @MockBean
    RegisterPaymentOutputPort registerPaymentOutputPort;
    @MockBean
    FindPlayerByCodeInputPort findPlayerByCodeInputPort;

    @MockBean
    FindPlayerByCodeOutputPort findPlayerByCodeOutputPort;

    @MockBean
    FindPaymentByCodeInputPort findPaymentByCodeInputPort;

    @MockBean
    ApprovePaymentInputPort approvePaymentInputPort;

    @MockBean
    DisapprovePaymentInputPort disapprovePaymentInputPort;

    private PaymentRequest paymentRequestValid;

    @BeforeAll
    static void setupBeforeClass() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void setupInitial() {
        paymentRequestValid = new PaymentRequest(LocalDate.now(), UUID.randomUUID().toString(), TypePaymentIndicator.PIX);
    }

    @AfterEach
    void setupFinal() {	}

    @Test
    void shouldRegisterPaymentAndReturnStatus201WhenRegisterPaymentValid() throws Exception{

        Mockito.when(registerPaymentInputPort.execute(any()))
                .thenReturn(new PaymentResponseDTO(1L, UUID.randomUUID().toString(),
                        LocalDate.now(), new PlayerResponseDTO(1L, UUID.randomUUID().toString(),
                        "Player 1", LocalDate.now(), "142587"), TypePaymentIndicator.PIX, StatusPaymentIndicator.PENDING));

        mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(paymentRequestValid)))
                .andDo(print())
                .andExpect(jsonPath("$.player", is("Player 1")))
                .andExpect(jsonPath("$.type", is(TypePaymentIndicator.PIX.toString())))
                .andExpect(jsonPath("$.status", is(StatusPaymentIndicator.PENDING.toString())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void shouldThrowExceptionWhenRegisterPaymentToPlayerNotExists() throws Exception{

        Mockito.when(registerPaymentInputPort.execute(any()))
                .thenThrow(PlayerNotExistsException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(paymentRequestValid)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRegisterPaymentAndReturnStatus400WhenRegisterPaymentInvalid() throws Exception{

        Mockito.when(registerPaymentInputPort.execute(any()))
                .thenReturn(new PaymentResponseDTO(1L, UUID.randomUUID().toString(),
                        LocalDate.now(), new PlayerResponseDTO(1L, UUID.randomUUID().toString(),
                        "Player 1", LocalDate.now(), "142587"), TypePaymentIndicator.PIX, StatusPaymentIndicator.PENDING));

        PaymentRequest paymentRequestInvalid = new PaymentRequest(LocalDate.now(), "", TypePaymentIndicator.PIX);

        mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(paymentRequestInvalid)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus200AndPaymentWhenFindPaymentByCode() throws Exception{

        String code = UUID.randomUUID().toString();

        Mockito.when(findPaymentByCodeInputPort.execute(code))
                .thenReturn(new PaymentResponseDTO(1L, code,
                        LocalDate.now(), new PlayerResponseDTO(1L, UUID.randomUUID().toString(),
                        "Player 1", LocalDate.now(), "142587"), TypePaymentIndicator.PIX, StatusPaymentIndicator.PENDING));

        mockMvc.perform(MockMvcRequestBuilders.get(URI.concat("/{code}"), code).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.player", is("Player 1")))
                .andExpect(jsonPath("$.type", is(TypePaymentIndicator.PIX.toString())))
                .andExpect(jsonPath("$.status", is(StatusPaymentIndicator.PENDING.toString())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenFindByCodePlayerNotExists() throws Exception{

        String code = UUID.randomUUID().toString();

        Mockito.when(findPaymentByCodeInputPort.execute(any()))
                .thenThrow(PaymentNotExistsException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(URI.concat("/{code}"), code).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldApprovePaymentWhenPaymentNotApprove() throws Exception {
        String code = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders.put(URI.concat("/{code}/approve"), code).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDisapprovePaymentWhenPaymentNotDisaApprove() throws Exception {
        String code = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders.delete(URI.concat("/{code}/disapprove"), code).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotExists() throws Exception{

        String code = UUID.randomUUID().toString();

        Mockito.when(disapprovePaymentInputPort.execute(code))
                .thenThrow(PaymentNotExistsException.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(URI.concat("/{code}/disapprove"), code).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowExceptionWhenApprovePaymentAlready() throws Exception{

        String code = UUID.randomUUID().toString();

        Mockito.when(approvePaymentInputPort.execute(any()))
                .thenThrow(BusinessException.class);

        mockMvc.perform(MockMvcRequestBuilders.put(URI.concat("/{code}/approve"), code).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
