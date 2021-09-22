package top.parak.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author KHighness
 * @since 2021-09-19
 */
@RestController
@RequestMapping("/rabbitmq/send")
public class MQController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 双端模型, 使用routing名称匹配queue名称，默认相等匹配
     * @see <a href="http://localhost:3333/rabbitmq/send/hello?msg=Khighness">hello</a>
     */
    @RequestMapping("/hello")
     @ResponseStatus(HttpStatus.OK)
    public String sendHello(@RequestParam("msg") String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.HELLO_ROUTING_KEY, message);
        log.info("『Hello』 send message: [{}]", message);
        return "success";
    }

    /**
     * 工作模型，多消费者，消费者轮询消费
     * @see <a href="http://localhost:3333/rabbitmq/send/work?msg=Khighness">work</a>
     */
    @RequestMapping("/work")
    @ResponseStatus(HttpStatus.OK)
    public String sendWork(@RequestParam("msg") String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.WORK_ROUTING_KEY, message);
        log.info("『Work』 send message: [{}]", message);
        return "success";
    }

    /**
     * 路由模型，根据routingKey精确匹配，routingKey未匹配的消息会丢失
     * @see <a href="http://localhost:3333/rabbitmq/send/direct?key=k&msg=Khighness">k</a>
     * @see <a href="http://localhost:3333/rabbitmq/send/direct?key=a&msg=Khighness">a</a>
     */
    @RequestMapping("/direct")
    @ResponseStatus(HttpStatus.OK)
    public String sendDirect(@RequestParam("key") String routingKey, @RequestParam("msg") String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, routingKey, message);
        log.info("『Direct』 send message: [{}], with key: [{}]", message, routingKey);
        return "success";
    }

    /**
     * 广播模型，不需要routingKey，消息推送到交换机绑定的所有队列
     * @see <a href="http://localhost:3333/rabbitmq/send/fanout?msg=Khighness">fanout</a>
     */
    @RequestMapping("/fanout")
    @ResponseStatus(HttpStatus.OK)
    public String sendFanout(@RequestParam("msg") String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, RabbitMQConfig.DEFAULT_ROUTING_KEY, message);
        log.info("『Fanout』 send message: [{}]", message);
        return "success";
    }

    /**
     * 主题模型，根据routingKey模糊匹配，推送到相应队列，routingKey未匹配的消息会丢失
     * @see <a href="http://localhost:3333/rabbitmq/send/topic?key=k.a.g&msg=Khighness">k.a.g</a>
     * @see <a href="http://localhost:3333/rabbitmq/send/topic?key=k.a.g.c&msg=Khighness">k.a.g.c</a>
     */
    @RequestMapping("/topic")
    @ResponseStatus(HttpStatus.OK)
    public String sendTopic(@RequestParam("key") String routingKey, @RequestParam("msg") String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, routingKey, message);
        log.info("『Topic』 send message: [{}], with key: [{}]", message, routingKey);
        return "success";
    }

    /**
     * 头部模型，不需要routingKey，根据header字段匹配，未匹配的消息会丢失
     * @see <a href="http://localhost:3333/rabbitmq/send/header?sign=Khighness&msg=Khighness">Khighness</a>
     * @see <a href="http://localhost:3333/rabbitmq/send/header?sign=follwerK&msg=Khighness">flowerK</a>
     */
    @RequestMapping("/header")
    @ResponseStatus(HttpStatus.OK)
    public String sendHeader(@RequestParam("sign") String signature, @RequestParam("msg") String message) {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("signature", signature);
        Message obj = new Message(message.getBytes(), properties);
        amqpTemplate.convertAndSend(RabbitMQConfig.HEADER_EXCHANGE, RabbitMQConfig.DEFAULT_ROUTING_KEY, obj);
        log.info("『Header』 send message: [{}], with sign: [{}]", message, signature);
        return "success";
    }

}
