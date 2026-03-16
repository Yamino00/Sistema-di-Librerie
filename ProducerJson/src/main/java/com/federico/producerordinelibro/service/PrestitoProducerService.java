package com.federico.producerordinelibro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.federico.producerordinelibro.dto.CopialibroDTO;
import com.federico.producerordinelibro.dto.PrestitoEventoDTO;
import com.federico.producerordinelibro.dto.PrestitoEventoDTO;
import com.federico.producerordinelibro.dto.UtenteDTO;
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

    @Scheduled(fixedRate = 10000) //millisecondi
    @Transactional
    public void InviaCopiaRandom() {
        String libriUrl = "http://localhost:8081/copialibro/copiedisponibili";
        String utentiUrl = "http://localhost:8081/utente/tuttiidUtente";

        try {
            CopialibroDTO[] copie = restTemplate.getForObject(libriUrl, CopialibroDTO[].class);
            Long[] idUtenti = restTemplate.getForObject(utentiUrl, Long[].class);

            if (idUtenti == null || idUtenti.length == 0) {
                log.error("Nessun utente disponibile per il prestito");
                return;
            }

            if (copie == null || copie.length == 0) {
                log.error("Nessuna copia disponibile per il prestito");
                return;
            }

            CopialibroDTO copiaScelta = copie[random.nextInt(copie.length)];
            Long idUtenteScelto = idUtenti[random.nextInt(idUtenti.length)];

            PrestitoEventoDTO evento = new PrestitoEventoDTO();
            evento.setCopia(copiaScelta);
            evento.setIdUtente(idUtenteScelto);

            String payload = objectMapper.writeValueAsString(evento);

            kafkaTemplate.send("Sync-Libro", payload).get();
            log.info("Richiesta inviata: Copia ID {} assegnata all'utente ID {}", copiaScelta.getId(), idUtenteScelto);

        } catch (Exception e) {
            log.error("Errore durante l'invio: {}", e.getMessage());
        }
    }
}