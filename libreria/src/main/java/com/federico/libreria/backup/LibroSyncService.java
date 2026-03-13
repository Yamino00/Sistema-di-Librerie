package com.federico.libreria.service;

import com.federico.libreria.document.LibroDocument;
import com.federico.libreria.dto.LibroDTO;
import com.federico.libreria.entity.Libro;
import com.federico.libreria.mapper.LibroMapper;
import com.federico.libreria.repository.LibroRepository;
import com.federico.libreria.mapper.LibroDocumentMapper;
import com.federico.libreria.repository.LibroDocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class LibroSyncService {

    private final LibroRepository postgresRepository;
    private final LibroMapper postgresMapper;

    private final LibroDocumentRepository elasticRepository;
    private final LibroDocumentMapper documentMapper;

    public LibroSyncService(LibroDocumentMapper documentMapper, LibroRepository postgresRepository, LibroMapper postgresMapper, LibroDocumentRepository elasticRepository) {
        this.documentMapper = documentMapper;
        this.postgresRepository = postgresRepository;
        this.postgresMapper = postgresMapper;
        this.elasticRepository = elasticRepository;
    }

    @Transactional
    @KafkaListener(topics = "Sync-Libro")
    public void syncdakafka(LibroDTO libroLetto) {

        log.info("Ricevuto nuovo libro da Kafka: {}", libroLetto.getNomeLibro());

        Libro libroEntity = postgresMapper.toEntity(libroLetto);
        Libro libroSalvato = postgresRepository.save(libroEntity);

        log.info("Salvato su Postgres con ID: {}", libroSalvato.getId());

        // Aggiorno l'id del dto con quello generato da Postgres
        libroLetto.setId(libroSalvato.getId());

        LibroDocument doc = documentMapper.toDocument(libroLetto);
        elasticRepository.save(doc);

        log.info("Salvato su Elasticsearch con ID: {}", doc.getId());
        log.info("Salvataggio eseguito sui db");
    }
}