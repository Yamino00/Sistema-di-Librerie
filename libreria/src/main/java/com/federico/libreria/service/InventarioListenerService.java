package com.federico.libreria.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.federico.libreria.dto.CopialibroDTO;
import jakarta.validation.Validator;
import com.federico.libreria.entity.Copialibro;
import com.federico.libreria.entity.Libreria;
import com.federico.libreria.entity.Libro;
import com.federico.libreria.repository.CopialibroRepository;
import com.federico.libreria.repository.LibreriaRepository;
import com.federico.libreria.repository.LibroRepository;
import jakarta.validation.ConstraintViolation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Set;

@Service
public class InventarioListenerService {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();
    private final Validator validator;

    private final CopialibroRepository copialibroRepository;
    private final LibroRepository libroRepository;
    private final LibreriaRepository libreriaRepository;

    public InventarioListenerService(Validator validator, CopialibroRepository copialibroRepository, LibroRepository libroRepository, LibreriaRepository libreriaRepository) {
        this.validator = validator;
        this.copialibroRepository = copialibroRepository;
        this.libroRepository = libroRepository;
        this.libreriaRepository = libreriaRepository;
    }

    @KafkaListener(topics = "censimento")
    @Transactional
    public void gestisciNuovoArrivo(byte[] payloadGrezzo) {
        System.out.println("Ricevuto messaggio grezzo: " + payloadGrezzo.length);

        try {
            String payload = new String(payloadGrezzo, StandardCharsets.UTF_8).trim();
            CopialibroDTO datiEstratti = null;

            // Riconosco il formato in base a come inizia la stringa
            if (payload.startsWith("{") || payload.startsWith("[")) {
                System.out.println("Rilevato formato: JSON");
                datiEstratti = jsonMapper.readValue(payload, CopialibroDTO.class);

            } else if (payload.startsWith("<")) {
                System.out.println("Rilevato formato: XML");
                datiEstratti = xmlMapper.readValue(payload, CopialibroDTO.class);

            } else if (payload.matches("^[0-9a-fA-F]+$") && payload.length() % 2 == 0) {
                System.out.println("Rilevato formato: HEX");

                // Per gli esadecimali:li trasformo in byte e poi in JSON
                byte[] decodedBytes = java.util.HexFormat.of().parseHex(payload);

                String jsonHex = new String(decodedBytes);
                System.out.println("Contenuto decodificato: " + jsonHex);

                // Uso il mapper JSON per leggere la stringa
                datiEstratti = jsonMapper.readValue(jsonHex, CopialibroDTO.class);
            } else {
                throw new IllegalArgumentException("Formato messaggio sconosciuto o non supportato");
            }

            // E qui aggiungo una validazione dei dati prima di fare il salvataggio
            if (datiEstratti != null) {
                Set<ConstraintViolation<CopialibroDTO>> violazioni = validator.validate(datiEstratti);

                if (!violazioni.isEmpty()) {
                    System.err.println("Dati nel messaggio non validi:");
                    for (ConstraintViolation<CopialibroDTO> violazione : violazioni) {
                        System.err.println(" - " + violazione.getMessage());
                    }
                    return;
                }

                salvaNuovaCopia(datiEstratti);
            }

        } catch (Exception e) {
            System.err.println("Errore durante l'elaborazione del messaggio: " + e.getMessage());
        }
    }
    // Questo metodo si occupa di salvare la nuova copia nel database, dopo aver verificato che il libro e la libreria esistano
    private void salvaNuovaCopia(CopialibroDTO dto) {
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("Impossibile salvare: Libro non trovato con ID " + dto.getLibroId()));

        Libreria libreria = libreriaRepository.findById(dto.getLibreriaId())
                .orElseThrow(() -> new RuntimeException("Impossibile salvare: nessuna Libreria trovata con ID " + dto.getLibreriaId()));

        Copialibro nuovaCopia = new Copialibro();
        nuovaCopia.setLibro(libro);
        nuovaCopia.setLibreria(libreria);
        nuovaCopia.setScaffale(dto.getScaffale());
        nuovaCopia.setRipiano(dto.getRipiano());

        copialibroRepository.save(nuovaCopia);

        System.out.println("Nuova copia salvata nel db con ID: " + nuovaCopia.getId());
    }
}