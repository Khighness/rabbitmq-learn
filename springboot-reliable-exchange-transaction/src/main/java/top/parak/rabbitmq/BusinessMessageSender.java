package top.parak.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author KHighness
 * @since 2021-09-21
 */
@Component
public class BusinessMessageSender {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {
        rabbitTemplate.setChannelTransacted(true);
    }

    @Transactional
    public void sendMsg(String msg) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_EXCHANGE, RabbitMQConfig.DEFAULT_ROUTING_KEY, msg);
        log.info("message: [{}]", msg);
        if (msg == null || msg.equals("") || msg.contains("exception")) {
            throw new RuntimeException("error");
        }
        log.info("message sent successfully: [{}]", msg);
    }
}
