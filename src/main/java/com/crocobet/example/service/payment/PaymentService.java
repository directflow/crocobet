package com.crocobet.example.service.payment;

import com.crocobet.example.domain.payment.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findAll();

    List<Payment> generatePayments();
}
