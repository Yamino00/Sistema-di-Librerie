package com.federico.libreria.service;

import com.federico.libreria.dto.UtenteDTO;
import com.federico.libreria.entity.Utente;
import com.federico.libreria.mapper.UtenteMapper;
import com.federico.libreria.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {
    private final UtenteRepository utenteRepository;
    private final UtenteMapper utenteMapper;

    public UtenteService(UtenteRepository utenteRepository, UtenteMapper utenteMapper) {
        this.utenteRepository = utenteRepository;
        this.utenteMapper = utenteMapper;
    }

    public List<UtenteDTO> findAllUtente() {
        return utenteRepository.findAll().stream()
                .map(utenteMapper::toDto)
                .toList();
    }

    public Optional<UtenteDTO> findUtenteById(Long id) {
        return utenteRepository.findById(id)
                .map(utenteMapper::toDto);
    }

    public UtenteDTO save(UtenteDTO nuovoUtenteDTO) {
        Utente nuovoUtente = utenteMapper.toEntity(nuovoUtenteDTO);
        Utente utenteSalvato = utenteRepository.save(nuovoUtente);
        return utenteMapper.toDto(utenteSalvato);
    }

    public boolean existsById(Long id) {
        return utenteRepository.existsById(id);
    }

    public void deleteByUtenteId(Long id) {
        utenteRepository.deleteById(id);
    }

    public Optional<UtenteDTO> findByEmail(String email) {
        return utenteRepository.findByEmail(email)
                .map(utenteMapper::toDto);
    }

    public Optional<UtenteDTO> updateUtente(Long id, UtenteDTO datiAggiornatDTO) {
        return utenteRepository.findById(id)
                .map(utenteEsistente -> {
                    utenteEsistente.setNome(datiAggiornatDTO.getNome());
                    utenteEsistente.setCognome(datiAggiornatDTO.getCognome());
                    utenteEsistente.setEmail(datiAggiornatDTO.getEmail());
                    utenteEsistente.setEta(datiAggiornatDTO.getEta());
                    utenteEsistente.setGenere(datiAggiornatDTO.getGenere());
                    Utente utenteAggiornato = utenteRepository.save(utenteEsistente);
                    return utenteMapper.toDto(utenteAggiornato);
                });
    }
}
