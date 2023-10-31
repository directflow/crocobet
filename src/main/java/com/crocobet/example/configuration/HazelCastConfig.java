package com.crocobet.example.configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelCastConfig {

    @Value("${hazelcast.host}")
    private String hazelcastHost;

    @Value("${hazelcast.port}")
    private Integer hazelcastPort;

    @Bean
    public ClientConfig clientConfig() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig
                .getNetworkConfig()
                .addAddress(hazelcastHost + ":" + hazelcastPort)
                .setSmartRouting(true)
                .setRedoOperation(true)
                .setConnectionTimeout(5000);
        return clientConfig;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}
