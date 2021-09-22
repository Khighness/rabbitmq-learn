package top.parak.death;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author KHighness
 * @since 2021-09-19
 */
@Component
public class BusinessMessageReceiver {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = DeadLetterConfig.BUSINESS_QUEUE1)
    public void receiveBusiness1(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("『business-1』 receive message: [{}]", msg);
        boolean ack = true;
        Exception exception = null;
        try {
            if (msg.contains("dead-letter")) {
                throw new RuntimeException("Dead Letter");
            }
        } catch (Exception e) {
            ack = false;
            exception = e;
        }
        if (!ack) {
            log.error("consume message occur exception: [{}]", exception.getMessage(), exception);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = DeadLetterConfig.BUSINESS_QUEUE2)
    public void reveiveBusiness2(Message message, Channel channel) throws IOException {
        log.info("『business-2』 receive message: [{}]", new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
