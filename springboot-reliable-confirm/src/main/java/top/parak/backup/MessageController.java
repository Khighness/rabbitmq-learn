package top.parak.backup;

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
     * @see <a href="http://localhost:9999/rabbitmq/send/confirm?msg=Khighness">success</a>
     * @see <a href="http://localhost:9999/rabbitmq/send/confirm?msg=Kexception">exception</a>
     */
    @RequestMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public String sendMsg(@RequestParam("msg") String msg) {
        businessMessageSender.sendMsg(msg);
        return "success";
    }
}
