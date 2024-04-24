package com.lima.api.soccer.adapter.input.api.controller;

import com.lima.api.soccer.adapter.doc.PaymentSwagger;
import com.lima.api.soccer.adapter.exception.handler.BusinessException;
import com.lima.api.soccer.adapter.exception.handler.ResourceNotFoundException;
import com.lima.api.soccer.adapter.input.api.controller.request.PaymentRequest;
import com.lima.api.soccer.adapter.input.api.controller.response.PaymentRepresentationDTO;
import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.exception.BusinessExeption;
import com.lima.api.soccer.application.exception.PaymentNotExistsException;
import com.lima.api.soccer.application.exception.PlayerNotExistsException;
import com.lima.api.soccer.application.port.payment.input.ApprovePaymentInputPort;
import com.lima.api.soccer.application.port.payment.input.DisapprovePaymentInputPort;
import com.lima.api.soccer.application.port.payment.input.FindPaymentByCodeInputPort;
import com.lima.api.soccer.application.port.payment.input.RegisterPaymentInputPort;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController implements PaymentSwagger {

    //TODO using lib convert object example: ModelMapper or MapStruct

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private final RegisterPaymentInputPort registerPaymentInputPort;
    private final FindPaymentByCodeInputPort findPaymentByCodeInputPort;
    private final ApprovePaymentInputPort approvePaymentInputPort;
    private final DisapprovePaymentInputPort disapprovePaymentInputPort;

    public PaymentController(RegisterPaymentInputPort registerPaymentInputPort, FindPaymentByCodeInputPort findPaymentByCodeInputPort,
                             ApprovePaymentInputPort approvePaymentInputPort, DisapprovePaymentInputPort disapprovePaymentInputPort) {
        this.registerPaymentInputPort = registerPaymentInputPort;
        this.findPaymentByCodeInputPort = findPaymentByCodeInputPort;
        this.approvePaymentInputPort = approvePaymentInputPort;
        this.disapprovePaymentInputPort = disapprovePaymentInputPort;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<PaymentRepresentationDTO> register(@Valid @RequestBody PaymentRequest request, UriComponentsBuilder uriBuilder) {

        //TODO check transactional propagation
        try{
            LOGGER.info(String.format("Received request to register payment do player code=%s", request.codePlayer()));
            PaymentResponseDTO paymentResponseDTO = registerPaymentInputPort.execute(request.toPaymentRequestDTO());
            PaymentRepresentationDTO paymentRepresentationDTO = new PaymentRepresentationDTO(paymentResponseDTO.code(), paymentResponseDTO.datePayment(),
                    paymentResponseDTO.playerResponseDTO().name(), paymentResponseDTO.typePayment(), paymentResponseDTO.statusPaymentIndicator());
            URI uri = uriBuilder.path("/v1/payments/{id}").buildAndExpand(paymentRepresentationDTO.code()).toUri();
            LOGGER.info(String.format("Payment code=%s registry in success", paymentRepresentationDTO.code()));
            return ResponseEntity.created(uri).body(paymentRepresentationDTO);
        }catch (PlayerNotExistsException ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @Override
    @GetMapping("/{code}")
    public ResponseEntity<PaymentRepresentationDTO> findByCode(@PathVariable("code") String code){

        try{
            LOGGER.info(String.format("Received request to find payment by code=%s", code));
            PaymentResponseDTO paymentResponseDTO = findPaymentByCodeInputPort.execute(code);
            PaymentRepresentationDTO paymentRepresentationDTO = new PaymentRepresentationDTO(paymentResponseDTO.code(), paymentResponseDTO.datePayment(),
                    paymentResponseDTO.playerResponseDTO().name(), paymentResponseDTO.typePayment(), paymentResponseDTO.statusPaymentIndicator());
            LOGGER.info(String.format("Payment code=%s find success", code));
            return ResponseEntity.ok(paymentRepresentationDTO);
        }catch (PaymentNotExistsException ex){
            LOGGER.error(String.format("Payment code=%s not found", code));
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @Override
    @PutMapping("/{code}/approve")
    public ResponseEntity<Void> approve(@PathVariable("code") String code){

        try{
            LOGGER.info(String.format("Received request to approve payment code=%s", code));
            approvePaymentInputPort.execute(code);
            LOGGER.info(String.format("Payment code=%s approved success", code));
            return ResponseEntity.noContent().build();
        }catch (PaymentNotExistsException paymentNotExistsException){
            LOGGER.error(String.format("Payment code=%s not exists", code));
            throw new ResourceNotFoundException(paymentNotExistsException.getMessage());
        }catch (BusinessExeption businessExeption){
            LOGGER.error(String.format("Payment code=%s already approve", code));
            throw new BusinessException(businessExeption.getMessage());
        }
    }

    @Override
    @DeleteMapping("/{code}/disapprove")
    public ResponseEntity<Void> disapprove(@PathVariable("code") String code){

        try{
            LOGGER.info(String.format("Received request to disapprove payment code=%s", code));
            disapprovePaymentInputPort.execute(code);
            LOGGER.info(String.format("Payment code=%s disapprove success", code));
            return ResponseEntity.noContent().build();
        }catch (PaymentNotExistsException paymentException){
            LOGGER.error(String.format("Payment code=%s not exists", code));
            throw new ResourceNotFoundException(paymentException.getMessage());
        }catch (BusinessExeption businessExeption){
            LOGGER.error(String.format("Payment code=%s already approve", code));
            throw new BusinessException(businessExeption.getMessage());
        }
    }
}
