package top.parak.backup;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KHighness
 * @since 2021-09-21
 */
@Configuration
public class RabbitMQConfig {
    public static final String BUSINESS_EXCHANGE = "parak.reliable.confirm.business.exchange";
    public static final String BUSINESS_QUEUE = "parak.reliable.confirm.business.queue";
    public static final String BUSINESS_KEY = "parak.reliable.confirm.business.key";

    /**
     * 声明业务交换机
     */
    @Bean("businessExchange")
    public DirectExchange businessExchange() {
        return new DirectExchange(BUSINESS_EXCHANGE);
    }

    @Bean("businessQueue")
    public Queue businessQueue() {
        return QueueBuilder.durable(BUSINESS_QUEUE).build();
    }

    @Bean
    public Binding businessBinding(@Qualifier("businessQueue") Queue queue, @Qualifier("businessExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(BUSINESS_KEY);
    }

}
