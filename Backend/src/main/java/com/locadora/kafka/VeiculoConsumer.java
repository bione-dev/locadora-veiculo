package com.locadora.kafka;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class VeiculoConsumer {

    private static final Logger LOGGER = Logger.getLogger(VeiculoConsumer.class.getName());

    @Incoming("veiculos-in")
    public void consumirMensagem(String mensagem) {
        // Registrar a mensagem recebida no log
        LOGGER.info("Mensagem recebida: " + mensagem);
    }
}
