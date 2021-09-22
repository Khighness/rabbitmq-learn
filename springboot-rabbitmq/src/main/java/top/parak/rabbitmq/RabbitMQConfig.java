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
public class RabbitMQConfig {

    public static final String DIRECT_EXCHANGE = "parak.exchange.direct";
    public static final String FANOUT_EXCHANGE = "parak.exchange.fanout";
    public static final String TOPIC_EXCHANGE = "parak.exchange.topic";
    public static final String HEADER_EXCHANGE = "parak.exchange.header";

    public static final String HELLO_QUEUE = "parak.queue.hello";
    public static final String WORK_QUEUE = "parak.queue.work1";
    public static final String DIRECT_QUEUE1 = "parak.queue.direct1";
    public static final String DIRECT_QUEUE2 = "parak.queue.direct2";
    public static final String FANOUT_QUEUE1 = "parak.queue.fanout1";
    public static final String FANOUT_QUEUE2 = "parak.queue.fanout2";
    public static final String TOPIC_QUEUE1 = "parak.queue.topic1";
    public static final String TOPIC_QUEUE2 = "parak.queue.topic2";
    public static final String HEADER_QUEUE = "parak.queue.header";

    public static final String DEFAULT_ROUTING_KEY = "";
    public static final String HELLO_ROUTING_KEY = "hello";
    public static final String WORK_ROUTING_KEY = "work";
    public static final String DIRECT_ROUTING_KEY1 = "k";
    public static final String DIRECT_ROUTING_KEY2 = "a";
    public static final String TOPIC_ROUTING_KEY1 = "k.a.*";
    public static final String TOPIC_ROUTING_KEY2 = "k.a.#";

    /**
     * 1. Direct Exchange：直连交换机，精确匹配路由，推送到相应队列
     */
    @Bean("directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean("helloQueue")
    public Queue helloQueue() {
        return new Queue(HELLO_QUEUE, true);
    }

    @Bean("workQueue")
    public Queue workQueue() {
        return new Queue(WORK_QUEUE, true);
    }

    @Bean("directQueue1")
    public Queue directQueue1() {
        return new Queue(DIRECT_QUEUE1, true);
    }

    @Bean("directQueue2")
    public Queue directQueue2() {
        return new Queue(DIRECT_QUEUE2, true);
    }

    @Bean
    public Binding directBinding1(@Qualifier("helloQueue") Queue queue, @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(HELLO_ROUTING_KEY);
    }

    @Bean
    public Binding directBinding2(@Qualifier("workQueue") Queue queue, @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(WORK_ROUTING_KEY);
    }

    @Bean
    public Binding directBinding3(@Qualifier("directQueue1") Queue queue, @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DIRECT_ROUTING_KEY1);
    }

    @Bean
    public Binding directBinding4(@Qualifier("directQueue2") Queue queue, @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DIRECT_ROUTING_KEY2);
    }

    /**
     * 2. Fanout Exchange: 广播交换机，不需要路由，发送到所有绑定过的队列队列
     */
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean("fanoutQueue1")
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE1, true);
    }

    @Bean("fanoutQueue2")
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2, true);
    }

    @Bean
    public Binding fanoutBinding1(@Qualifier("fanoutQueue1") Queue queue, @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding fanoutBinding2(@Qualifier("fanoutQueue2") Queue queue, @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * 3. Topic Exchange：主题交换机，模糊匹配路由，推送到相应队列
     */
    @Bean("topicExchange")
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean("topicQueue1")
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean("topicQueue2")
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public Binding topicBinding1(@Qualifier("topicQueue1") Queue queue, @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(topicExchange()).with(TOPIC_ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2(@Qualifier("topicQueue2") Queue queue, @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(TOPIC_ROUTING_KEY2);
    }

    /**
     * 4. Header Exchange: 头部交换机，根据header匹配，推送到相应队列
     */
    @Bean("headersExchange")
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADER_EXCHANGE);
    }

    @Bean("headerQueue")
    public Queue headerQueue() {
        return new Queue(HEADER_QUEUE, true);
    }

    @Bean
    public Binding headerBinding(@Qualifier("headerQueue") Queue queue, @Qualifier("headersExchange") HeadersExchange exchange) {
        Map<String, Object> map = new HashMap<>();
        map.put("signature", "Khighness");
        return BindingBuilder.bind(queue).to(exchange).whereAny(map).match();
    }

}
