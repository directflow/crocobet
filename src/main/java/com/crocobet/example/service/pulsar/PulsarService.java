package com.crocobet.example.service.pulsar;

import com.crocobet.example.domain.payment.Payment;
import com.crocobet.example.model.pulsar.PulsarUserModel;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;

public interface PulsarService {
    void sendLogAsync(String logModelString) throws PulsarClientException;

    MessageId sendAddUser(PulsarUserModel pulsarUserModel) throws PulsarClientException;

    void sendPaymentAsync(Payment payment) throws PulsarClientException;
}
