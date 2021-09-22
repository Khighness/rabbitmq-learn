package top.parak.transaction;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KHighness
 * @since 2021-09-21
 */
@Configuration
public class RabbitMQConfig {
    public static final String BUSINESS_EXCHANGE = "parak.reliable.transaction.business.exchange";
    public static final String BUSINESS_QUEUE = "parak.reliable.transaction.business.queue";
    public static final String DEFAULT_ROUTING_KEY = "";

    /**
     * 声明业务交换机
     */
    @Bean("businessExchange")
    public FanoutExchange businessExchange() {
        return new FanoutExchange(BUSINESS_EXCHANGE);
    }

    @Bean("businessQueue")
    public Queue businessQueue() {
        return QueueBuilder.durable(BUSINESS_QUEUE).build();
    }

    @Bean
    public Binding businessBinding(@Qualifier("businessQueue") Queue queue, @Qualifier("businessExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * 启用RabbitMQ事务
     */
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }

}
