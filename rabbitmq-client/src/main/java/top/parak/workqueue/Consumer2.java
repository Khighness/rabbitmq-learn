package top.parak.workqueue;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.parak.common.RabbitMQUtil;

import java.io.IOException;

/**
 * @author KHighness
 * @since 2021-05-24
 */

public class Consumer2 {
    private static Logger log = LoggerFactory.getLogger(Consumer2.class);
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.queueDeclare("work", true, false, false, null);
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.debug("消费者-2：{}", new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
