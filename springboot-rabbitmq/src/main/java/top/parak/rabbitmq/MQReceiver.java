package top.parak.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author KHighness
 * @since 2021-09-19
 */
@Component
public class MQReceiver {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = RabbitMQConfig.HELLO_QUEUE)
    public void receiveHello(String message) {
        log.info("『Hello』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.WORK_QUEUE)
    public void receiveWork1(String message) {
        log.info("『Work-1』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.WORK_QUEUE)
    public void receiveWork2(String message) {
        log.info("『Work-2』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.DIRECT_QUEUE1)
    public void receiveDirect1(String message) {
        log.info("『Direct-1』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.DIRECT_QUEUE1)
    public void receiveDirect2(String message) {
        log.info("『Direct-2』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.FANOUT_QUEUE1)
    public void receiveFanout1(String message) {
        log.info("『Fanout-1』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.FANOUT_QUEUE2)
    public void receiveFanout2(String message) {
        log.info("『Fanout-2』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info("『Topic-1』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info("『Topic-2』 receive message: [{}]", message);
    }

    @RabbitListener(queues = RabbitMQConfig.HEADER_QUEUE)
    public void receiveHeader(String message) {
        log.info("『Header』 receive message: [{}]", message);
    }
}
