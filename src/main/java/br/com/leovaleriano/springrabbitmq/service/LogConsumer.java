package br.com.leovaleriano.springrabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogConsumer {

    @RabbitListener(queues = "${application.queue.name}")
    public void consumirMensagem(String mensagem) {
        log.info("Consumindo a mensagem: " + mensagem);
    }
}
