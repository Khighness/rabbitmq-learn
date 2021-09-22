package top.parak.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author KHighness
 * @since 2021-05-24
 */

public class RabbitMQUtil {
    private static volatile ConnectionFactory connectionFactory = null;
    private static final String HOST = "192.168.117.155";
    private static final int PORT = 5672;
    private static final String VIRTUAL_HOST = "/learn";
    private static final String USERNAME = "parak";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        if (connectionFactory == null) {
            synchronized (RabbitMQUtil.class) {
                if (connectionFactory == null) {
                    connectionFactory = new ConnectionFactory();
                    connectionFactory.setHost(HOST);
                    connectionFactory.setPort(PORT);
                    connectionFactory.setVirtualHost(VIRTUAL_HOST);
                    connectionFactory.setUsername(USERNAME);
                    connectionFactory.setPassword(PASSWORD);
                }
            }
        }
        try {
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnectionAndChannel(Channel channel, Connection connection) {
        try {
            if (connection != null)
                channel.close();
            if (connection != null)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
