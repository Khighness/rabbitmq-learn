package top.parak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author KHighness
 * @since 2021-05-25
 */
@SpringBootApplication
public class RabbitDeadLetterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitDeadLetterApplication.class, args);
    }
}
