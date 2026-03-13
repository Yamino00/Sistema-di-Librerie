package com.federico.libreria.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.federico.libreria.dto.CopialibroDTO;
import com.federico.libreria.entity.Copialibro;
import com.federico.libreria.entity.Prestito;
import com.federico.libreria.entity.Utente;
import com.federico.libreria.repository.CopialibroRepository;
import com.federico.libreria.repository.PrestitoRepository;
import com.federico.libreria.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class PrestitoConsumerService {

    private final CopialibroRepository copialibroRepository;
    private final PrestitoRepository prestitoRepository;
    private final UtenteRepository utenteRepository;
    private final ObjectMapper objectMapper;

    public PrestitoConsumerService(CopialibroRepository copialibroRepository, PrestitoRepository prestitoRepository, UtenteRepository utenteRepository) {
        this.copialibroRepository = copialibroRepository;
        this.prestitoRepository = prestitoRepository;
        this.utenteRepository = utenteRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "Sync-Libro")
    @Transactional
    public void elaboraPrestito(String payload) {
        try {
            CopialibroDTO dto = objectMapper.readValue(payload, CopialibroDTO.class);

            Copialibro copia = copialibroRepository.findById(dto.getId()).orElse(null);
            if (copia == null) {
                log.error("La copia con ID {} non esiste.", dto.getId());
                return;
            }

            long idUtenteRandom = ThreadLocalRandom.current().nextLong(2, 14);

            Utente utente = utenteRepository.findById(idUtenteRandom).orElse(null);
            if (utente == null) {
                return;
            }

            Prestito nuovoPrestito = new Prestito();
            nuovoPrestito.setCopialibro(copia);
            nuovoPrestito.setUtente(utente);
            nuovoPrestito.setDataPrestito(new Timestamp(System.currentTimeMillis()));

            prestitoRepository.save(nuovoPrestito);

            log.info("Prestito registrato con successo: Copia ID {} assegnata all'utente ID {}", copia.getId(), utente.getId());

        } catch (Exception e) {
            log.error("Errore durante l'elaborazione del prestito: {}", e.getMessage());
        }
    }
}