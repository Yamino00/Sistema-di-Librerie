package com.federico.producerhex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.federico.producerhex.dto.CopialibroDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HexFormat;

@RestController
@RequestMapping("/hex")
public class HexProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper jsonMapper;

    public HexProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = new ObjectMapper(); // Ci serve per il primo passaggio
    }

    @PostMapping("/invia")
    public String inviaMessaggioHex(@RequestBody String payloadHex) {
        try {
            kafkaTemplate.send("censimento", payloadHex).get();
            return "Stringa HEX inviata a Kafka con successo: " + payloadHex;
        } catch (Exception e) {
            return "Errore durante l'invio a Kafka: " + e.getMessage();
        }
    }
}