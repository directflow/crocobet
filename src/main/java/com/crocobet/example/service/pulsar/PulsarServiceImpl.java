package com.crocobet.example.service.pulsar;

import com.crocobet.example.model.pulsar.PulsarUserModel;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
public class PulsarServiceImpl implements PulsarService {

    @Value("${spring.pulsar.log-topic-name}")
    private String logTopicName;

    @Value("${spring.pulsar.user-topic-name}")
    private String userTopicName;

    private PulsarTemplate<String> pulsarTemplateLog;

    private PulsarTemplate<PulsarUserModel> pulsarTemplateUser;

    @Autowired
    public void setPulsarTemplateLog(PulsarTemplate<String> pulsarTemplateLog) {
        this.pulsarTemplateLog = pulsarTemplateLog;
    }

    @Autowired
    public void setPulsarTemplateUser(PulsarTemplate<PulsarUserModel> pulsarTemplateUser) {
        this.pulsarTemplateUser = pulsarTemplateUser;
    }

    /**
     * Send log data to Pulsar Async
     *
     * @param logModelString String model
     * @throws PulsarClientException On pulsar exception
     */
    @Override
    public void sendLogAsync(String logModelString) throws PulsarClientException {

        pulsarTemplateLog
                .newMessage(logModelString)
                .withTopic(logTopicName)
                .sendAsync();
    }

    /**
     * Send PulsarUserDomain to Pulsar
     * Internal consumer is listening in PulsarInternalListener class
     *
     * @param pulsarUserModel PulsarUserDomain object
     * @throws PulsarClientException On pulsar exception
     */
    @Override
    public MessageId sendAddUser(PulsarUserModel pulsarUserModel) throws PulsarClientException {
        return pulsarTemplateUser
                .newMessage(pulsarUserModel)
                .withTopic(userTopicName)
                .withSchema(Schema.JSON(PulsarUserModel.class))
                .send();
    }
}