package com.federico.libreria.service;

import com.federico.libreria.dto.LibroDTO;
import com.federico.libreria.entity.Libro;
import com.federico.libreria.mapper.LibroMapper;
import com.federico.libreria.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;

    public LibroService(LibroRepository libroRepository, LibroMapper libroMapper) {
        this.libroRepository = libroRepository;
        this.libroMapper = libroMapper;
    }

    public List<LibroDTO> findAllLibro() {
        return libroRepository.findAll().stream()
                .map(libroMapper::toDto)
                .toList();
    }

    public Optional<LibroDTO> findLibroById(Long id) {
        return libroRepository.findById(id)
                .map(libroMapper::toDto);
    }

    public LibroDTO save(LibroDTO nuovoLibroDTO) {
        Libro nuovoLibro = libroMapper.toEntity(nuovoLibroDTO);
        Libro libroSalvato = libroRepository.save(nuovoLibro);
        return libroMapper.toDto(libroSalvato);
    }

    public boolean existsById(Long id) {
        return libroRepository.existsById(id);
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }

    public Optional<LibroDTO> updateLibro(Long id, LibroDTO datiAggiornatDTO) {
        return libroRepository.findById(id)
                .map(libroEsistente -> {
                    libroEsistente.setNomeLibro(datiAggiornatDTO.getNomeLibro());
                    libroEsistente.setTrama(datiAggiornatDTO.getTrama());
                    libroEsistente.setAnno(datiAggiornatDTO.getAnno());
                    libroEsistente.setGenere(datiAggiornatDTO.getGenere());
                    libroEsistente.setAutore(datiAggiornatDTO.getAutore());
                    Libro libroAggiornato = libroRepository.save(libroEsistente);
                    return libroMapper.toDto(libroAggiornato);
                });
    }
}
