package com.federico.libreria.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.federico.libreria.dto.LibreriaDTO;
import com.federico.libreria.entity.Libreria;
import com.federico.libreria.mapper.LibreriaMapper;
import com.federico.libreria.repository.LibreriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LibreriaService {

    private final LibreriaRepository libreriaRepository;
    private final LibreriaMapper libreriaMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public LibreriaService(LibreriaRepository libreriaRepository, LibreriaMapper libreriaMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.libreriaRepository = libreriaRepository;
        this.libreriaMapper = libreriaMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public List<LibreriaDTO> findAllLibreria() {
        return libreriaRepository.findAll().stream()
                .map(libreriaMapper::toDto)
                .toList();
    }

    public Optional<LibreriaDTO> findLibreriaById(Long id) {
        return libreriaRepository.findById(id)
                .map(libreriaMapper::toDto);
    }

    public LibreriaDTO save(LibreriaDTO nuovaLibreriaDTO) {
        try {
            Libreria nuovaLibreria = libreriaMapper.toEntity(nuovaLibreriaDTO);
            Libreria libreriaSalvata = libreriaRepository.save(nuovaLibreria);
            LibreriaDTO dtoSalvato = libreriaMapper.toDto(libreriaSalvata);
            String savelibreria = objectMapper.writeValueAsString(dtoSalvato);
            kafkaTemplate.send("SyncElastic", savelibreria);
            return dtoSalvato;
        } catch (Exception e) {
            log.error("Errore durante la creazione della libreria: {}", e.getMessage());
        }
        return null;
    }


    public boolean existsById(Long id) {
        return libreriaRepository.existsById(id);
    }

    public void deleteById(Long id) {
        libreriaRepository.deleteById(id);
    }

    public Optional<LibreriaDTO> updateLibreria(Long id, LibreriaDTO datiAggiornatDTO) {
        return libreriaRepository.findById(id)
                .map(libreriaEsistente -> {
                    libreriaEsistente.setNome(datiAggiornatDTO.getNome());
                    libreriaEsistente.setIndirizzo(datiAggiornatDTO.getIndirizzo());
                    libreriaEsistente.setCitta(datiAggiornatDTO.getCitta());
                    Libreria libreriaAggiornata = libreriaRepository.save(libreriaEsistente);
                    return libreriaMapper.toDto(libreriaAggiornata);
                });
    }

}
