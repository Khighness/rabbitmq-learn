package top.parak.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author KHighness
 * @since 2021-09-19
 */
@Component
public class BusinessMessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg) {
        rabbitTemplate.convertAndSend(DeadLetterConfig.BUSINESS_EXCHANGE, "", msg);
    }

}
