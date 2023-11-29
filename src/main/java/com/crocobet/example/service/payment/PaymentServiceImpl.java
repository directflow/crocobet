package com.crocobet.example.service.payment;

import com.crocobet.example.domain.payment.Payment;
import com.crocobet.example.repository.PaymentRepository;
import com.crocobet.example.service.pulsar.PulsarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PulsarService pulsarService;

    private final PaymentRepository paymentRepository;

    /**
     * Find all payments from db
     *
     * @return List of Payment entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    /**
     * Generate 10 Payment objects with random values and send to pulsar async
     * External example-flink application is listening with Flink pulsar listener
     *
     * @return List of Payment objects
     */
    @Override
    public List<Payment> generatePayments() {
        return IntStream
                .iterate(0, i -> ++i)
                .limit(10)
                .mapToObj(i -> buildAndSend())
                .toList();
    }

    /**
     * Build Payment object with random values and send to pulsar sync
     *
     * @return Payment object
     */
    private Payment buildAndSend() {
        return send(buildPayment());
    }

    /**
     * Build Payment object with random values
     *
     * @return Payment object
     */
    private Payment buildPayment() {
        return Payment
                .builder()
                .transactionId(System.nanoTime())
                .createdAt(System.nanoTime())
                .amount(randomDouble(1, 1000))
                .build();
    }

    /**
     * Send Payment object to pulsar async
     *
     * @param payment Payment object
     * @return Payment object
     */
    private Payment send(Payment payment) {
        try {
            pulsarService.sendPaymentAsync(payment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }

    /**
     * Generate random double
     *
     * @param min Min size
     * @param max Max size
     * @return Rounded double
     */
    private Double randomDouble(double min, double max) {
        return new BigDecimal((ThreadLocalRandom.current().nextDouble() * (max - min)) + min).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
