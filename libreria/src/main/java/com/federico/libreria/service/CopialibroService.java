package com.federico.libreria.service;

import com.federico.libreria.dto.CopialibroDTO;
import com.federico.libreria.entity.Copialibro;
import com.federico.libreria.entity.Libreria;
import com.federico.libreria.entity.Libro;
import com.federico.libreria.mapper.CopialibroMapper;
import com.federico.libreria.repository.CopialibroRepository;
import com.federico.libreria.repository.LibreriaRepository;
import com.federico.libreria.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CopialibroService {
    private final CopialibroRepository copialibroRepository;
    private final LibroRepository libroRepository;
    private final LibreriaRepository libreriaRepository;
    private final CopialibroMapper copialibroMapper;

    public CopialibroService(CopialibroMapper copialibroMapper, CopialibroRepository copialibroRepository, LibroRepository libroRepository, LibreriaRepository libreriaRepository) {
        this.copialibroMapper = copialibroMapper;
        this.copialibroRepository = copialibroRepository;
        this.libroRepository = libroRepository;
        this.libreriaRepository = libreriaRepository;
    }

    public List<CopialibroDTO> findAllCopialibro() {
        return copialibroRepository.findAll().stream()
                .map(copialibroMapper::toDto)
                .toList();
    }

    public Optional<CopialibroDTO> findCopialibroById(Long id) {
        return copialibroRepository.findById(id)
                .map(copialibroMapper::toDto);
    }

    public List<CopialibroDTO> findCopiadisponiblie() {
        return copialibroRepository.findTutteCopieDisponibili().stream()
                .map(copialibroMapper::toDto)
                .toList();

    }

    public List<Long> findCopiaIddisponiblie() {
        return copialibroRepository.findTutteCopieIdDisponibili();
    }

    public CopialibroDTO save(CopialibroDTO nuovoCopialibroDTO) {
        Copialibro nuovoCopialibro = new Copialibro();
        nuovoCopialibro.setScaffale(nuovoCopialibroDTO.getScaffale());
        nuovoCopialibro.setRipiano(nuovoCopialibroDTO.getRipiano());

        if (nuovoCopialibroDTO.getLibroId() != null) {
            Libro libro = libroRepository.findById(nuovoCopialibroDTO.getLibroId())
                    .orElseThrow(() -> new IllegalArgumentException("Libro non trovato con id: " + nuovoCopialibroDTO.getLibroId()));
            nuovoCopialibro.setLibro(libro);
        }

        if (nuovoCopialibroDTO.getLibreriaId() != null) {
            Libreria libreria = libreriaRepository.findById(nuovoCopialibroDTO.getLibreriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Libreria non trovata con id: " + nuovoCopialibroDTO.getLibreriaId()));
            nuovoCopialibro.setLibreria(libreria);
        }

        Copialibro copialibroSalvato = copialibroRepository.save(nuovoCopialibro);
        return copialibroMapper.toDto(copialibroSalvato);
    }

    public boolean existsById(Long id) {
        return copialibroRepository.existsById(id);
    }

    public void deleteById(Long id) {
        copialibroRepository.deleteById(id);
    }

    public Optional<CopialibroDTO> updateCopialibro(Long id, CopialibroDTO datiAggiornatDTO) {
        return copialibroRepository.findById(id)
                .map(copialibroEsistente -> {
                    copialibroEsistente.setScaffale(datiAggiornatDTO.getScaffale());
                    copialibroEsistente.setRipiano(datiAggiornatDTO.getRipiano());

                    if (datiAggiornatDTO.getLibroId() != null) {
                        Libro libro = libroRepository.findById(datiAggiornatDTO.getLibroId())
                                .orElseThrow(() -> new IllegalArgumentException("Libro non trovato con id: " + datiAggiornatDTO.getLibroId()));
                        copialibroEsistente.setLibro(libro);
                    }

                    if (datiAggiornatDTO.getLibreriaId() != null) {
                        Libreria libreria = libreriaRepository.findById(datiAggiornatDTO.getLibreriaId())
                                .orElseThrow(() -> new IllegalArgumentException("Libreria non trovata con id: " + datiAggiornatDTO.getLibreriaId()));
                        copialibroEsistente.setLibreria(libreria);
                    }

                    Copialibro copialibroAggiornato = copialibroRepository.save(copialibroEsistente);
                    return copialibroMapper.toDto(copialibroAggiornato);
                });
    }
}
