package top.parak.delay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author KHighness
 * @since 2021-09-20
 */
@RequestMapping("/rabbitmq/send")
@RestController
public class MessageController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DelayMessageSender delayMessageSender;

    /**
     * 发送延时消息，按照类型
     * @see <a href="http://localhost:7777/rabbitmq/send/delay?msg=khighness&type=1">delay 3s</a>
     * @see <a href="http://localhost:7777/rabbitmq/send/delay?msg=khighness&type=2">delay 10s</a>
     */
    @RequestMapping("/delay")
    @ResponseStatus(HttpStatus.OK)
    public String sendMsg(@RequestParam("msg") String message, @RequestParam("type") Integer type) {
        DelayType delayType = DelayType.getDelayType(type);
        if (delayType == null)
            throw new IllegalArgumentException("type is in [1, 2]");
        log.info("now: [{}], receive message: [{}], type: [{}]", new Date(), message, delayType);
        delayMessageSender.sendMsg(message, delayType);
        return "success";
    }

    /**
     * 发送延时消息，按照时间
     * @see <a href="http://localhost:7777/rabbitmq/send/time?msg=khighness&time=1000">delay 3s</a>
     * @see <a href="http://localhost:7777/rabbitmq/send/time?msg=khighness&time=5000">delay 5s</a>
     */
    @RequestMapping("/time")
    @ResponseStatus(HttpStatus.OK)
    public String sendMsg(@RequestParam("msg") String message, @RequestParam("time") Long time) {
        log.info("now: [{}], receive message: [{}], time: [{}]", new Date(), message, time);
        delayMessageSender.sendMsg(message, time);
        return "success";
    }

    /**
     * 发送延时消息，按照时间
     * @see <a href="http://localhost:7777/rabbitmq/send/delayed?msg=khighness&time=1000">delay 3s</a>
     * @see <a href="http://localhost:7777/rabbitmq/send/delayed?msg=khighness&time=5000">delay 5s</a>
     */
    @RequestMapping("/delayed")
    @ResponseStatus(HttpStatus.OK)
    public String sendMsgToDelayedExchange(@RequestParam("msg") String message, @RequestParam("time") Long time) {
        log.info("now: [{}], receive message: [{}], time: [{}]", new Date(), message, time);
        delayMessageSender.sendMsgToDelayedExchange(message, time);
        return "success";
    }
}
