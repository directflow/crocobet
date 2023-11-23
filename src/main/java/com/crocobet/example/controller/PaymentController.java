package com.crocobet.example.controller;

import com.crocobet.example.domain.payment.Payment;
import com.crocobet.example.logging.Loggable;
import com.crocobet.example.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment controller")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Loggable
    @GetMapping("/")
    @Operation(summary = "Get all payments")
    public ResponseEntity<List<Payment>> findAll() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @Loggable
    @GetMapping("/generate")
    @Operation(summary = "Generate payments and send to Pulsar")
    public ResponseEntity<List<Payment>> generatePayments() {
        return ResponseEntity.ok(paymentService.generatePayments());
    }
}
