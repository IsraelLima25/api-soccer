package com.lima.api.soccer.adapter.output.database.model;

import com.lima.api.soccer.application.dto.payment.response.PaymentResponseDTO;
import com.lima.api.soccer.application.dto.player.response.PlayerResponseDTO;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tbl_payment")
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "date_paid")
    private LocalDate datePayment;

    @ManyToOne
    @JoinColumn(name = "id_player")
    private PlayerModel player;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypePaymentIndicator typePaymentIndicator;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPaymentIndicator statusPaymentIndicator;

    @Deprecated
    public PaymentModel(){ }

    public PaymentModel(Long id, String code, LocalDate datePayment, PlayerModel player, TypePaymentIndicator typePaymentIndicator, StatusPaymentIndicator statusPaymentIndicator) {
        this.id = id;
        this.code = code;
        this.datePayment = datePayment;
        this.player = player;
        this.typePaymentIndicator = typePaymentIndicator;
        this.statusPaymentIndicator = statusPaymentIndicator;
    }

    public PaymentModel(LocalDate datePayment, PlayerModel player, TypePaymentIndicator typePaymentIndicator, StatusPaymentIndicator statusPaymentIndicator) {
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

    public PlayerModel getPlayer() {
        return player;
    }

    public TypePaymentIndicator getTypePaymentIndicator() {
        return typePaymentIndicator;
    }

    public StatusPaymentIndicator getStatusPaymentIndicator() {
        return statusPaymentIndicator;
    }

    public PaymentResponseDTO toPaymentResponseDTO(){
        return new PaymentResponseDTO(id, code, datePayment, new PlayerResponseDTO(player.getId(), player.getCode(), player.getName(), player.getDob(), player.getRg()), typePaymentIndicator, statusPaymentIndicator);
    }

    @PrePersist
    private void loadingCode() {
        this.code = UUID.randomUUID().toString();
    }
}
