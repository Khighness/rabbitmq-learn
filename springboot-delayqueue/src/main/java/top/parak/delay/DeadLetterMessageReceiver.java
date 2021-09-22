package top.parak.delay;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author KHighness
 * @since 2021-09-20
 */
@Component
public class DeadLetterMessageReceiver {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = DelayQueueConfig.DEAD_LETTER_QUEUE1)
    public void receiveDeadLetter1(Message message, Channel channel) throws IOException {
        log.info("now: [{}], 『dead letter-1』 receive message: [{}]", new Date(), new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DelayQueueConfig.DEAD_LETTER_QUEUE2)
    public void receiveDeadLetter2(Message message, Channel channel) throws IOException {
        log.info("now: [{}], 『dead letter-2』 receive message: [{}]", new Date(), new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DelayQueueConfig.DEAD_LETTER_QUEUE3)
    public void receiveDeadLetter3(Message message, Channel channel) throws IOException {
        log.info("now: [{}], 『dead letter-3』 receive message: [{}]", new Date(), new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
