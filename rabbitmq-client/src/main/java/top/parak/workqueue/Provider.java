package top.parak.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import top.parak.common.RabbitMQUtil;

import java.io.IOException;

/**
 * @author KHighness
 * @since 2021-05-24
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", "work", null, new String("message" + (i + 1) + " => hello, khighness").getBytes());
        }
        RabbitMQUtil.closeConnectionAndChannel(channel, connection);
    }
}
