package com.federico.producerordinelibro.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Slf4j
@Service
public class PrestitoRestituzioneService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Random random = new Random();

    public PrestitoRestituzioneService(RestTemplate restTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10000)
    public void restituisciLibroRandom() {

        String prestitiAttiviUrl = "http://localhost:8081/prestito/tutti/id/attivi";

        try {

            Long[] idPrestiti = restTemplate.getForObject(prestitiAttiviUrl, Long[].class);

            if (idPrestiti == null) {
                log.info("Nessun libro attualmente in prestito");
                return;
            }

            Long idPrestitodaRestituire = idPrestiti[random.nextInt(idPrestiti.length)];

            String payload = String.valueOf(idPrestitodaRestituire);

            // Creazione Header
            Message<String> messaggio = MessageBuilder
                    .withPayload(payload)
                    .setHeader(KafkaHeaders.TOPIC, "Prestito")
                    .setHeader("TIPO_OPERAZIONE", "RESTITUZIONE")
                    .build();

            kafkaTemplate.send(messaggio).get();

            log.info("Richiesta di restituzione inviata: ID Prestito: {}", idPrestitodaRestituire);

        } catch (Exception e) {
            log.error("Errore durante l'invio della restituzione: {}", e.getMessage());
        }
    }

}
