package top.parak.direct;

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
        // 指定交换机
        channel.exchangeDeclare("routing", "direct");
        // 发送消息
        channel.basicPublish("routing", "info", null, "direct => info".getBytes());
        channel.basicPublish("routing", "debug", null, "direct => debug".getBytes());
        channel.basicPublish("routing", "warn", null, "direct => warn".getBytes());
        channel.basicPublish("routing", "error", null, "direct => error".getBytes());
        RabbitMQUtil.closeConnectionAndChannel(channel, connection);
    }
}
