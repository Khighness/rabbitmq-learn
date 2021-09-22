package top.parak.direct;

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
        Channel channel = connection.createChannel();
        // 指定交换机
        channel.exchangeDeclare("routing", "direct");
        // 临时队列
        String queue = channel.queueDeclare().getQueue();
        // 基于route key绑定队列和交换机
        channel.queueBind(queue, "routing", "debug");
        channel.queueBind(queue, "routing", "warn");
        channel.queueBind(queue, "routing", "error");
        // 获取消费的消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.debug("消费者-2：{}", new String(body));
            }
        });
    }
}
