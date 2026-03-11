package com.federico.libreria.service;

import com.federico.libreria.dto.LibreriaDTO;
import com.federico.libreria.entity.Libreria;
import com.federico.libreria.mapper.LibreriaMapper;
import com.federico.libreria.repository.LibreriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibreriaService {

    private final LibreriaRepository libreriaRepository;
    private final LibreriaMapper libreriaMapper;

    public LibreriaService(LibreriaRepository libreriaRepository, LibreriaMapper libreriaMapper) {
        this.libreriaRepository = libreriaRepository;
        this.libreriaMapper = libreriaMapper;
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
        Libreria nuovaLibreria = libreriaMapper.toEntity(nuovaLibreriaDTO);
        Libreria libreriaSalvata = libreriaRepository.save(nuovaLibreria);
        return libreriaMapper.toDto(libreriaSalvata);
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
