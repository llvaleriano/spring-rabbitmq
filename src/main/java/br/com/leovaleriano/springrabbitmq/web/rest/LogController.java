package br.com.leovaleriano.springrabbitmq.web.rest;

import br.com.leovaleriano.springrabbitmq.domain.Mensagem;
import br.com.leovaleriano.springrabbitmq.service.LogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
@AllArgsConstructor
@Slf4j
public class LogController {

    private final LogService logService;

    @PostMapping
    public void sendMessage(@RequestBody Mensagem mensagem) {
        log.info("Mensagem recebida via rest");
        logService.enviarMensagem(mensagem.getTexto());
    }

}
