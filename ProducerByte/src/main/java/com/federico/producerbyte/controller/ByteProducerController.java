package com.federico.producerbyte.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/byte")
public class ByteProducerController {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public ByteProducerController(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/invia")
    public String inviaMessaggioHex(@RequestBody byte[] payload) {
        try {
            kafkaTemplate.send("censimento", payload).get();
            return "Messaggio inviato a Kafka con successo: " + payload.length;
        } catch (Exception e) {
            return "Errore durante l'invio a Kafka: " + e.getMessage();
        }
    }
}