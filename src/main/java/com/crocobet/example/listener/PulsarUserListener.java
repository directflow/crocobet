package com.crocobet.example.listener;

import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.model.pulsar.PulsarUserModel;
import com.crocobet.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Component;

import static com.crocobet.example.builder.UserDomainBuilder.buildRequestDTO;

@Component
@RequiredArgsConstructor
public class PulsarUserListener {

    private final UserService userService;

    /**
     * Listen for add user action from Pulsar
     * Build UserDomain from PulsarUserDomain object
     * Call userService addUser method for adding new user
     *
     * @param pulsarUserModel PulsarUserDomain object
     */
    @PulsarListener(
            topics = "${spring.pulsar.user-topic-name}",
            subscriptionType = SubscriptionType.Failover,
            schemaType = SchemaType.JSON
    )
    public void addUserListen(PulsarUserModel pulsarUserModel) {

        try {
            userService.addUser(buildRequestDTO(pulsarUserModel));
        } catch (UserDuplicateException userDuplicateException) {
            System.out.println(userDuplicateException.getMessage());
        }
    }
}
