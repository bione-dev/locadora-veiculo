package com.locadora.kafka;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VeiculoProducer {

    @Channel("veiculos-out")
    Emitter<String> emitter;

    public void enviarMensagem(String mensagem) {
        emitter.send(mensagem);
    }
}
