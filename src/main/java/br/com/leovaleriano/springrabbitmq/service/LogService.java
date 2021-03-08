package br.com.leovaleriano.springrabbitmq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${application.queue.name}")
    private String queueName;

    public void enviarMensagem(String mensagem) {
        rabbitTemplate.convertAndSend(queueName, mensagem);
    }

}
