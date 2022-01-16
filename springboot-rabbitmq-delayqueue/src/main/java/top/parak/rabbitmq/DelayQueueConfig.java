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
public class DelayQueueConfig {
    public static final String DELAY_EXCHANGE = "parak.delay.queue.business.exchange";
    public static final String DELAY_QUEUE1 = "parak.delay.queue.business.queue1";
    public static final String DELAY_QUEUE2 = "parak.delay.queue.business.queue2";
    public static final String DELAY_QUEUE_ROUTING_KEY1 = "parak.delay.queue.business.key1";
    public static final String DELAY_QUEUE_ROUTING_KEY2 = "parak.delay.queue.business.key2";

    public static final String DEAD_LETTER_EXCHANGE = "parak.delay.queue.deadletter.exchange";
    public static final String DEAD_LETTER_QUEUE1 = "parak.delay.queue.deadletter.queue1";
    public static final String DEAD_LETTER_QUEUE2 = "parak.delay.queue.deadletter.queue2";
    public static final String DEAD_LETTER_ROUTING_KEY1 = "parak.delay.queue.deadletter.key1";
    public static final String DEAD_LETTER_ROUTING_KEY2 = "parak.delay.queue.deadletter.key2";

    public static final String DELAY_QUEUE3 = "parak.delay.queue.business.queue3";
    public static final String DELAY_QUEUE_ROUTING_KEY3 = "parak.delay.queue.business.key3";
    public static final String DEAD_LETTER_QUEUE3 = "parak.delay.queue.deadletter.queue3";
    public static final String DEAD_LETTER_ROUTING_KEY3 = "parak.delay.queue.deadletter.key3";

    public static final String DELAYED_MESSAGE_EXCHANGE = "parak.delay.queue.delayed.message.exchange";
    public static final String DELAYED_MESSAGE_QUEUE = "parak.delay.queue.delayed.message.queue";
    public static final String DELAYED_MESSAGE_ROUTING_KEY = "parak.delay.queue.delayed.message.key";

    /**
     * 声明延时交换机
     */
    @Bean("delayExchange")
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean("delayQueue1")
    public Queue delayQueue1() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY1);
        map.put("x-message-ttl", 3000);
        return QueueBuilder.durable(DELAY_QUEUE1).withArguments(map).build();
    }

    @Bean("delayQueue2")
    public Queue delayQueue2() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY2);
        map.put("x-message-ttl", 10000);
        return QueueBuilder.durable(DELAY_QUEUE2).withArguments(map).build();
    }

    // 不设置TTL
    @Bean("delayQueue3")
    public Queue delayQueue3() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY3);
        return QueueBuilder.durable(DELAY_QUEUE3).withArguments(map).build();
    }

    @Bean
    public Binding delayBinding1(@Qualifier("delayQueue1") Queue queue, @Qualifier("delayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUE_ROUTING_KEY1);
    }

    @Bean
    public Binding delayBinding2(@Qualifier("delayQueue2") Queue queue, @Qualifier("delayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUE_ROUTING_KEY2);
    }

    @Bean
    public Binding delayBinding3(@Qualifier("delayQueue3") Queue queue, @Qualifier("delayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUE_ROUTING_KEY3);
    }

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

    @Bean("deadLetterQueue3")
    public Queue deadLetterQueue3() {
        return new Queue(DEAD_LETTER_QUEUE3);
    }

    @Bean
    public Binding deadLetterBinding1(@Qualifier("deadLetterQueue1") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ROUTING_KEY1);
    }

    @Bean
    public Binding deadLetterBinding2(@Qualifier("deadLetterQueue2") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ROUTING_KEY2);
    }

    @Bean
    public Binding deadLetterBinding3(@Qualifier("deadLetterQueue3") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ROUTING_KEY3);
    }

    /**
     * 声明自定义交换机
     */
    @Bean("delayedMessageExchange")
    public CustomExchange delayedMessageExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_MESSAGE_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean("delayedMessageQueue")
    public Queue delayedMessageQueue() {
        return new Queue(DELAYED_MESSAGE_QUEUE);
    }

    @Bean
    public Binding delayedMessageBinding(@Qualifier("delayedMessageQueue") Queue queue, @Qualifier("delayedMessageExchange") CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAYED_MESSAGE_ROUTING_KEY).noargs();
    }

}
