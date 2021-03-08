package br.com.leovaleriano.springrabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${application.queue.name}")
    private String queueName;

    @Bean
    public Queue logQueue() {
        return new Queue(queueName, false);
    }

}
