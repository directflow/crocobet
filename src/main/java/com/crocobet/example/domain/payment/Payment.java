package com.crocobet.example.domain.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments", indexes = {@Index(name = "indexTransactionId", columnList = "transaction_id")})
public class Payment {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "flink_stream", nullable = false)
    private String flinkStream;

    @Column(name = "amount", nullable = false)
    private Double amount;

}
