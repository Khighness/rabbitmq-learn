package top.parak.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author KHighness
 * @since 2021-05-23
 * @apiNote 生产者
 */

public class Provider {

    private static ConnectionFactory connectionFactory;

    public static void init() {
        // 创建MQ连接工厂
        connectionFactory = new ConnectionFactory();
        // 设置服务器IP:PORT
        connectionFactory.setHost("192.168.117.155");
        connectionFactory.setPort(5672);
        // 设置连接虚拟主机
        connectionFactory.setVirtualHost("/hello");
        // 设置用户密码
        connectionFactory.setUsername("top/parak");
        connectionFactory.setPassword("123456");
    }

    public static void testSendMessage() throws IOException, TimeoutException {
        // 获取连接对象
        Connection connection = connectionFactory.newConnection();
        // 获取连接通道
        Channel channel = connection.createChannel();
        // 通道绑定队列
        // 参数1：队列名称，如果队列不存在则自动创建
        // 参数2：用来定义队列特性是否要持久化
        // 参数3：是否独占队列，只允许当前连接可用
        // 参数4：是否在消费完成后自动删除队列
        // 参数5：附加参数
        channel.queueDeclare("hello", true, false, false, null);
        // 发布消息
        // 参数1：交换机名称
        // 参数2：队列名称
        // 参数3：传递的额外设置
        // 参数4：消息的具体内容
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello, highness".getBytes());
        // 关闭通道
        channel.close();
        // 关闭连接
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        init();
        testSendMessage();
    }

}
