package top.parak.backup;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author KHighness
 * @since 2021-09-21
 */
@Component
public class BusinessWarningMessageReceiver {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = RabbitMQConfig.BUSINESS_BACKUP_WARNING_QUEUE)
    public void sendMsg(Message message, Channel channel) throws IOException {
        log.error("『warning』 receive unroutable message: [{}]", new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
