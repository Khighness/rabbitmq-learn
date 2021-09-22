package top.parak.workqueue;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.parak.common.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author KHighness
 * @since 2021-05-24
 */

public class Consumer1 {
    private static Logger log = LoggerFactory.getLogger(Consumer1.class);
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.getConnection();
        final Channel channel = connection.createChannel();
        // 一次只能消费一条消息
        channel.basicQos(1);
        channel.queueDeclare("work", true, false, false, null);
        // 第二个参数，关闭消息自动确认
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.debug("消费者-1：{}", new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
