package com.lima.api.soccer.application.entity;

import com.lima.api.soccer.application.exception.BusinessExeption;
import com.lima.api.soccer.application.indicator.StatusPaymentIndicator;
import com.lima.api.soccer.application.indicator.TypePaymentIndicator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void shouldCreatePaymentWhenPaymentValid(){

        Payment payment = new Payment(LocalDate.now(), new Player("Player 1", LocalDate.now(), "14547854"), TypePaymentIndicator.COIN);

        assertEquals(LocalDate.now(), payment.getDatePayment());
        assertEquals(TypePaymentIndicator.COIN, payment.getTypePaymentIndicator());
        assertEquals(StatusPaymentIndicator.PENDING, payment.getStatusPaymentIndicator());
    }

    @Test
    void shouldApprovePaymentWhenPaymentNotApprove(){

        Payment payment = new Payment(LocalDate.now(), new Player("Player 1", LocalDate.now(), "14547854"), TypePaymentIndicator.COIN);
        payment.approve();

        assertEquals(StatusPaymentIndicator.APPROVE, payment.getStatusPaymentIndicator());
    }

    @Test
    void shouldThrowBusinessExceptionWhenApprovePaymentAlreadyApprove(){

        Payment payment = new Payment(LocalDate.now(), new Player("Player 1", LocalDate.now(), "14547854"), TypePaymentIndicator.COIN);
        payment.approve();

        assertThrows(BusinessExeption.class, payment::approve);

    }

    @Test
    void shouldDisapprovePaymentWhenPaymentNotDisapprove(){

        Payment payment = new Payment(LocalDate.now(), new Player("Player 1", LocalDate.now(), "14547854"), TypePaymentIndicator.COIN);
        payment.disapprove();

        assertEquals(StatusPaymentIndicator.DISAPPROVE, payment.getStatusPaymentIndicator());
    }

    @Test
    void shouldThrowBusinessExceptionWhenDisapprovePaymentAlreadyDisapprove(){
        Payment payment = new Payment(LocalDate.now(), new Player("Player 1", LocalDate.now(), "14547854"), TypePaymentIndicator.COIN);
        payment.disapprove();

        assertThrows(BusinessExeption.class, payment::disapprove);
    }
}