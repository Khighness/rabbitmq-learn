package top.parak.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KHighness
 * @since 2021-09-21
 */
@RequestMapping("/rabbitmq/send")
@RestController
public class MessageController {
    @Autowired
    private BusinessMessageSender businessMessageSender;

    /**
     * @see <a href="http://localhost:11111/rabbitmq/send/queue?key=key&msg=Khighness">succeed</a>
     * @see <a href="http://localhost:11111/rabbitmq/send/queue?key=kkk&msg=FlowerK">fail</a>
     */
    @RequestMapping("/queue")
    @ResponseStatus(HttpStatus.OK)
    public String sendMsg(@RequestParam("key") String routingKey, @RequestParam("msg") String message) {
        businessMessageSender.sendMsg(routingKey, message);
        return "success";
    }

}
