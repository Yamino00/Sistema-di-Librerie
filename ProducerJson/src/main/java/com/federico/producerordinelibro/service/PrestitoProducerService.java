package com.federico.producerordinelibro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.Long;
import com.federico.producerordinelibro.dto.PrestitoEventoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void InviaCopiaRandom() {
        String libriUrl = "http://localhost:8081/copialibro/disponibili/id";
        String utentiUrl = "http://localhost:8081/utente/tutti/id";

        try {
            Long[] idCopie = restTemplate.getForObject(libriUrl, Long[].class);
            Long[] idUtenti = restTemplate.getForObject(utentiUrl, Long[].class);

            if (idUtenti == null) {
                log.error("Nessun utente disponibile per il prestito");
                return;
            }

            if (idCopie == null) {
                log.error("Nessuna copia disponibile per il prestito");
                return;
            }

            Long idCopiaScelta = idCopie[random.nextInt(idCopie.length)];
            Long idUtenteScelto = idUtenti[random.nextInt(idUtenti.length)];

            PrestitoEventoDTO evento = new PrestitoEventoDTO();
            evento.setIdCopia(idCopiaScelta);
            evento.setIdUtente(idUtenteScelto);

            String payload = objectMapper.writeValueAsString(evento);

            kafkaTemplate.send("Sync-Libro", payload).get();
            log.info("Richiesta inviata: Copia ID {} assegnata all'utente ID {}", idCopiaScelta, idUtenteScelto);

        } catch (Exception e) {
            log.error("Errore durante l'invio: {}", e.getMessage());
        }
    }
}