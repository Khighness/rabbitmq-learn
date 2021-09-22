package top.parak.death;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KHighness
 * @since 2021-09-19
 */
@RequestMapping("/rabbitmq/send")
@RestController
public class MessageController {
    @Autowired
    private BusinessMessageSender businessMessageSender;

    /**
     * 发送业务消息
     * @see <a href="http://localhost:5555/rabbitmq/send/dead?msg=khighness">business</a>
     * @see <a href="http://localhost:5555/rabbitmq/send/dead?msg=dead-letter">dead-letter</a>
     */
    @RequestMapping("/dead")
    @ResponseStatus(HttpStatus.OK)
    public String sendMsg(@RequestParam("msg") String message) {
        businessMessageSender.sendMsg(message);
        return "success";
    }
}
