package com.federico.producerordinelibro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.federico.producerordinelibro.dto.CopialibroDTO; // Usa il tuo package
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Slf4j
@Service
public class PrestitoProducerService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    public PrestitoProducerService(RestTemplate restTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Scheduled(fixedRate = 60000) //millisecondi
    public void InviaCopiaRandom() {
        String apiUrl = "http://localhost:8081/copialibro/copiedisponibili";

        try {
            CopialibroDTO[] copie = restTemplate.getForObject(apiUrl, CopialibroDTO[].class);

            if (copie != null && copie.length > 0) {
                CopialibroDTO copiaScelta = copie[random.nextInt(copie.length)];

                String payload = objectMapper.writeValueAsString(copiaScelta);

                kafkaTemplate.send("Sync-Libro", payload).get();
                log.info("Copia ID {} inviata a Kafka con successo", copiaScelta.getId());
            }
        } catch (Exception e) {
            log.error("Errore durante l'invio a Kafka: {}", e.getMessage());
        }
    }
}