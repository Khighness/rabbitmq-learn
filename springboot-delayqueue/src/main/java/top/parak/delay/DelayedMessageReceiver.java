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
 * @since 2021-09-21
 */
@Component
public class DelayedMessageReceiver {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = DelayQueueConfig.DELAYED_MESSAGE_QUEUE)
    public void receiveDeadLetter1(Message message, Channel channel) throws IOException {
        log.info("now: [{}], 『delayed message』 receive message: [{}]", new Date(), new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
