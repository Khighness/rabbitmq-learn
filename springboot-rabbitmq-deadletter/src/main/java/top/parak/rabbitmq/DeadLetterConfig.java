package top.parak.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KHighness
 * @since 2021-09-19
 */
@Configuration
public class DeadLetterConfig {
    public static final String BUSINESS_EXCHANGE = "parak.dead.letter.business.exchange";
    public static final String BUSINESS_QUEUE1 = "parak.dead.letter.business.queue1";
    public static final String BUSINESS_QUEUE2 = "parak.dead.letter.business.queue2";
    public static final String DEAD_LETTER_EXCHANGE = "parak.dead.letter.death.exchange";
    public static final String DEAD_LETTER_QUEUE1 = "parak.dead.letter.death.queue1";
    public static final String DEAD_LETTER_QUEUE2 = "parak.dead.letter.death.queue2";
    public static final String DEAD_LETTER_ROUTING_KEY1 = "parak.dead.letter.death.key1";
    public static final String DEAD_LETTER_ROUTING_KEY2 = "parak.dead.letter.death.key2";


    /**
     * 声明死信交换机
     */
    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean("deadLetterQueue1")
    public Queue deadLetterQueue1() {
        return new Queue(DEAD_LETTER_QUEUE1);
    }

    @Bean("deadLetterQueue2")
    public Queue deadLetterQueue2() {
        return new Queue(DEAD_LETTER_QUEUE2);
    }

    @Bean
    public Binding deadLetterBinding1(@Qualifier("deadLetterQueue1") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ROUTING_KEY1);
    }


    /**
     * 声明业务交换机
     */
    @Bean("businessExchange")
    public FanoutExchange businessExchange() {
        return new FanoutExchange(BUSINESS_EXCHANGE);
    }

    @Bean("businessQueue1")
    public Queue businessQueue1() {
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange 声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key 声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY1);
        return QueueBuilder.durable(BUSINESS_QUEUE1).withArguments(args).build();
    }

    @Bean("businessQueue2")
    public Queue businessQueue2() {
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange 声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key 声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY2);
        return QueueBuilder.durable(BUSINESS_QUEUE2).withArguments(args).build();
    }

    @Bean
    public Binding businessBinding1(@Qualifier("businessQueue1") Queue queue, @Qualifier("businessExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding businessBinding2(@Qualifier("businessQueue2") Queue queue, @Qualifier("businessExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding deadLetterBinding2(@Qualifier("deadLetterQueue2") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ROUTING_KEY1);
    }

}
