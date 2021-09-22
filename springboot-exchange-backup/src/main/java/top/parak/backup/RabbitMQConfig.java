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
    public static final String BUSINESS_EXCHANGE = "parak.exchange.backup.business.exchange";
    public static final String BUSINESS_QUEUE = "parak.exchange.backup.business.queue";
    public static final String BUSINESS_KEY = "key";
    public static final String BUSINESS_BACKUP_EXCHANGE = "parak.exchange.backup.backup.exchange";
    public static final String BUSINESS_BACKUP_QUEUE = "parak.exchange.backup.backup.queue";
    public static final String BUSINESS_BACKUP_WARNING_QUEUE = "parak.exchange.backup.backup.warning.queue";

    /**
     * 声明业务交换机
     */
    @Bean("businessExchange")
    public DirectExchange businessExchange() {
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.directExchange(BUSINESS_EXCHANGE)
                .durable(true)
                // 设置备份交换机
                .withArgument("alternate-exchange", BUSINESS_BACKUP_EXCHANGE);
        return (DirectExchange) exchangeBuilder.build();
    }

    @Bean("businessQueue")
    public Queue businessQueue() {
        return QueueBuilder.durable(BUSINESS_QUEUE).build();
    }

    @Bean
    public Binding businessBinding(@Qualifier("businessQueue") Queue queue, @Qualifier("businessExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(BUSINESS_KEY);
    }

    /**
     * 声明备份交换机
     */
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.fanoutExchange(BUSINESS_BACKUP_EXCHANGE)
                .durable(true);
        return exchangeBuilder.build();
    }

    @Bean("backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BUSINESS_BACKUP_QUEUE).build();
    }

    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(BUSINESS_BACKUP_WARNING_QUEUE).build();
    }

    @Bean
    public Binding backupBinding(@Qualifier("backupQueue") Queue queue, @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding warningBinding(@Qualifier("warningQueue") Queue queue, @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}
