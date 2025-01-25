package com.locadora.kafka;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class VeiculoProducer {

    private static final Logger LOGGER = Logger.getLogger(VeiculoProducer.class.getName());

    @Channel("veiculos-out")
    Emitter<String> emitter;

    public void enviarMensagem(String mensagem) {
        // Verificação de mensagem nula ou vazia
        if (mensagem == null || mensagem.isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode ser nula ou vazia");
        }

        // Registrar a mensagem enviada no log
        LOGGER.info("Enviando mensagem: " + mensagem);
        emitter.send(mensagem);
    }
}
