package top.parak.helloworld;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author KHighness
 * @since 2021-05-23
 * @apiNote 消费者
 */
public class Consumer {
    private final static Logger log = LoggerFactory.getLogger(Consumer.class);

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

    public static void testConsumeMessage() throws IOException, TimeoutException {
        // 创建连接对象
        Connection connection = connectionFactory.newConnection();
        // 创建连接通道
        Channel channel = connection.createChannel();
        // 通道绑定队列
        channel.queueDeclare("hello", true, false, false, null);
        // 消费消息
        // 参数1：消费的队列名称
        // 参数2：开始消息的自动确认机制
        // 参数3：消费时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            // 参数1：标签
            // 参数2：信封
            // 参数3：属性
            // 参数4：消息队列中取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.debug("消费消息：{}", new String(body));
            }
        });
        // 需要不断接收队列中的消息，所以不关闭通道和连接
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        init();
        testConsumeMessage();
    }
}
