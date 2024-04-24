package com.lima.api.soccer.application.entity;

import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.exception.BusinessExeption;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;

import java.time.LocalDate;

public class Payment {

    private Long id;
    private String code;
    private LocalDate datePayment;
    private Player player;
    private TypePaymentIndicator typePaymentIndicator;

    private StatusPaymentIndicator statusPaymentIndicator;

    public Payment(LocalDate datePayment, Player player, TypePaymentIndicator typePaymentIndicator) {
        this.datePayment = datePayment;
        this.player = player;
        this.typePaymentIndicator = typePaymentIndicator;
        this.statusPaymentIndicator = StatusPaymentIndicator.PENDING;
    }

    public Payment(Long id, String code, LocalDate datePayment, Player player, TypePaymentIndicator typePaymentIndicator, StatusPaymentIndicator statusPaymentIndicator) {
        this.id = id;
        this.code = code;
        this.datePayment = datePayment;
        this.player = player;
        this.typePaymentIndicator = typePaymentIndicator;
        this.statusPaymentIndicator = statusPaymentIndicator;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getDatePayment() {
        return datePayment;
    }

    public Player getPlayer() {
        return player;
    }

    public TypePaymentIndicator getTypePaymentIndicator() {
        return typePaymentIndicator;
    }

    public StatusPaymentIndicator getStatusPaymentIndicator() {
        return statusPaymentIndicator;
    }

    public void approve() {
        if(this.statusPaymentIndicator != StatusPaymentIndicator.APPROVE){
            this.statusPaymentIndicator = StatusPaymentIndicator.APPROVE;
        }else{
            throw new BusinessExeption("This payment has already been approved");
        }
    }

    public void disapprove() {
        if(this.statusPaymentIndicator != StatusPaymentIndicator.DISAPPROVE){
            this.statusPaymentIndicator = StatusPaymentIndicator.DISAPPROVE;
        }else{
            throw new BusinessExeption("This payment has already been disapprove");
        }
    }

    public PaymentResponseDTO toResponseDTO() {
        return new PaymentResponseDTO(id, code, datePayment, player.toResponseDTO(), typePaymentIndicator, statusPaymentIndicator);
    }
}
