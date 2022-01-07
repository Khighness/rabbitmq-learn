package top.parak.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author KHighness
 * @since 2021-09-21
 */
@Component
public class BusinessMessageSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    public void sendMsg(String routingKey, String message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("id: [{}], message: [{}]", correlationData.getId(), message);
        // 设置消息ID，用于回调时的判断
        rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_EXCHANGE, routingKey, message, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (b) {
            log.info("message confirm succeed, id: [{}]", id);
        } else {
            log.info("message confirm failed, id: [{}], cause: [{}]", id, s);
        }
    }

    @Override
    public void returnedMessage(Message message, int relayCode, String replyText, String exchange, String routingKey) {
        log.info("message is returned. msg: [{}], replayCode: [{}], replyText: [{}], exchange: [{}], routingKey: [{}]",
                new String(message.getBody()), relayCode, replyText, exchange, routingKey);
    }

}
