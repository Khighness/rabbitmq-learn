package top.parak.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import top.parak.common.RabbitMQUtil;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author KHighness
 * @since 2021-05-24
 */

public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
        // 指定交换机
        // 参数1：交换机名称，不存在则自动创建
        // 参数2：交换机类型
        channel.exchangeDeclare("pubsub", "fanout");
        // 发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            channel.basicPublish("pubsub", "", null, s.getBytes());
        }
        RabbitMQUtil.closeConnectionAndChannel(channel, connection);
    }
}
