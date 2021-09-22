package top.parak.topics;

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
        channel.exchangeDeclare("topics", "topic");
        channel.basicPublish("topics", "k.a", null, "topics => k.a".getBytes());
        channel.basicPublish("topics", "k.a.g", null, "topics => k.a.g".getBytes());
        RabbitMQUtil.closeConnectionAndChannel(channel, connection);
    }
}
