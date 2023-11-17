package com.crocobet.example.listener;

import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Component;

@Component
public class PulsarLogListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PulsarLogListener.class);

    /**
     * Listen for logs from Pulsar
     * Print log message
     *
     * @param message Message object
     */
    @PulsarListener(topics = "${spring.pulsar.log-topic-name}", subscriptionType = SubscriptionType.Shared)
    public void listen(Message<String> message) {
        LOGGER.info(message.getValue());
    }
}
