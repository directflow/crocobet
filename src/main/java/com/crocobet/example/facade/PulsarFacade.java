package com.crocobet.example.facade;

import com.crocobet.example.builder.PulsarUserModelBuilder;
import com.crocobet.example.model.pulsar.PulsarUserModel;
import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.service.pulsar.PulsarService;
import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PulsarFacade {

    private final PulsarService pulsarService;

    /**
     * Build PulsarUserDomain from UserDomain object
     * Call pulsarService sendAddUser method
     *
     * @param userDomain UserDomain object
     * @throws PulsarClientException On pulsar exception
     */
    public MessageId addUser(UserDomain userDomain) throws PulsarClientException {

        PulsarUserModel pulsarUserModel = PulsarUserModelBuilder.buildPulsarUserDomain(userDomain);

        return pulsarService.sendAddUser(pulsarUserModel);
    }
}
