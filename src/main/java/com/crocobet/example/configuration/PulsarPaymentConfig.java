package com.crocobet.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.PulsarTopic;

@Configuration
public class PulsarPaymentConfig {

    @Value("${spring.pulsar.payment-topic-name}")
    private String pulsarPaymentTopicName;

    @Bean
    public PulsarTopic paymentTopicBuilder() {
        return PulsarTopic.builder(pulsarPaymentTopicName).build();
    }
}
