package top.parak.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author KHighness
 * @since 2021-09-20
 */
@Component
public class DelayMessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg, DelayType type) {
        switch (type) {
            case DELAY_3_SECONDS:
                rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_EXCHANGE, DelayQueueConfig.DELAY_QUEUE_ROUTING_KEY1, msg);
                break;
            case DELAY_10_SECONDS:
                rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_EXCHANGE, DelayQueueConfig.DELAY_QUEUE_ROUTING_KEY2, msg);
                break;
        }
    }

    public void sendMsg(String msg, long delayTime) {
        rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_EXCHANGE, DelayQueueConfig.DELAY_QUEUE_ROUTING_KEY3, msg, m -> {
            m.getMessageProperties().setExpiration(Long.toString(delayTime));
            return m;
        });
    }

    public void sendMsgToDelayedExchange(String msg, long delayTime) {
        rabbitTemplate.convertAndSend(DelayQueueConfig.DELAYED_MESSAGE_EXCHANGE, DelayQueueConfig.DELAYED_MESSAGE_ROUTING_KEY, msg, m -> {
            m.getMessageProperties().setExpiration(Long.toString(delayTime));
            return m;
        });
    }

}
