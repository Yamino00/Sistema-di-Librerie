package com.federico.libreria.service;

import com.federico.libreria.dto.PrestitoDTO;
import com.federico.libreria.entity.Copialibro;
import com.federico.libreria.entity.Prestito;
import com.federico.libreria.entity.PrestitoStorico;
import com.federico.libreria.entity.Utente;
import com.federico.libreria.mapper.PrestitoMapper;
import com.federico.libreria.repository.CopialibroRepository;
import com.federico.libreria.repository.PrestitoRepository;
import com.federico.libreria.repository.PrestitoStoricoRepository;
import com.federico.libreria.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PrestitoService {

    private final PrestitoRepository prestitoRepository;
    private final CopialibroRepository copialibroRepository;
    private final UtenteRepository utenteRepository;
    private final PrestitoStoricoRepository prestitoStoricoRepository;
    private final PrestitoMapper prestitoMapper;

    public PrestitoService(CopialibroRepository copialibroRepository, PrestitoRepository prestitoRepository, UtenteRepository utenteRepository, PrestitoStoricoRepository prestitoStoricoRepository, PrestitoMapper prestitoMapper) {
        this.copialibroRepository = copialibroRepository;
        this.prestitoRepository = prestitoRepository;
        this.utenteRepository = utenteRepository;
        this.prestitoStoricoRepository = prestitoStoricoRepository;
        this.prestitoMapper = prestitoMapper;
    }

    public List<PrestitoDTO> findAllPrestito() {
        return prestitoRepository.findAll().stream()
                .map(prestitoMapper::toDto)
                .toList();
    }

    @Scheduled(fixedRate = 10000)
    public List<PrestitoDTO> saveStorico() {
        List<Prestito> prestitiTerminati = prestitoRepository.findPrestitiTerminati();

        // Mappatura verso Cassandra
        List<PrestitoStorico> storicoDaSalvare = prestitiTerminati.stream()
                .map(prestito -> {
                    PrestitoStorico storico = new PrestitoStorico();
                    storico.setIdPrestito(prestito.getId());
                    storico.setDataEvento(Instant.now());
                    storico.setTipoEvento("ARCHIVIAZIONE_STORICO");
                    storico.setIdCopialibro(prestito.getCopialibro().getId());
                    storico.setIdUtente(prestito.getUtente().getId());
                    storico.setDataPrestito(prestito.getDataPrestito().toInstant());
                    storico.setDataRestituzione(prestito.getDataRestituzione().toInstant());
                    return storico;
                })
                .toList();
        prestitoStoricoRepository.saveAll(storicoDaSalvare);
        prestitoRepository.deleteAll(prestitiTerminati);
        try {
            return prestitiTerminati.stream()
                    .map(prestitoMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.info("I Presiti terminati non sono stati archiviati correttamente: {}", e.getMessage());
        }
        return null;
    }

    public Optional<PrestitoDTO> findPrestitoById(Long id) {
        return prestitoRepository.findById(id)
                .map(prestitoMapper::toDto);
    }

    public List<PrestitoDTO> findAllPrestitoAttivo() {
        return prestitoRepository.findPrestitiAttivi().stream()
                .map(prestitoMapper::toDto)
                .toList();
    }

    public PrestitoDTO save(PrestitoDTO nuovoPrestitoDTO) {
        Prestito nuovoPrestito = new Prestito();
        nuovoPrestito.setDataPrestito(nuovoPrestitoDTO.getDataPrestito());
        nuovoPrestito.setDataRestituzione(nuovoPrestitoDTO.getDataRestituzione());

        if (nuovoPrestitoDTO.getCopialibroId() != null) {
            Copialibro copialibro = copialibroRepository.findById(nuovoPrestitoDTO.getCopialibroId())
                    .orElseThrow(() -> new IllegalArgumentException("Copialibro non trovato con id: " + nuovoPrestitoDTO.getCopialibroId()));
            nuovoPrestito.setCopialibro(copialibro);
        }

        if (nuovoPrestitoDTO.getUtenteId() != null) {
            Utente utente = utenteRepository.findById(nuovoPrestitoDTO.getUtenteId())
                    .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con id: " + nuovoPrestitoDTO.getUtenteId()));
            nuovoPrestito.setUtente(utente);
        }

        Prestito prestitoSalvato = prestitoRepository.save(nuovoPrestito);
        return prestitoMapper.toDto(prestitoSalvato);
    }

    public boolean existsById(Long id) {
        return prestitoRepository.existsById(id);
    }

    public void deleteById(Long id) {
        prestitoRepository.deleteById(id);
    }

    public Optional<PrestitoDTO> updatePrestito(Long id, PrestitoDTO datiAggiornatDTO) {
        return prestitoRepository.findById(id)
                .map(prestitoEsistente -> {
                    prestitoEsistente.setDataPrestito(datiAggiornatDTO.getDataPrestito());
                    prestitoEsistente.setDataRestituzione(datiAggiornatDTO.getDataRestituzione());

                    if (datiAggiornatDTO.getCopialibroId() != null) {
                        Copialibro copialibro = copialibroRepository.findById(datiAggiornatDTO.getCopialibroId())
                                .orElseThrow(() -> new IllegalArgumentException("Copialibro non trovato con id: " + datiAggiornatDTO.getCopialibroId()));
                        prestitoEsistente.setCopialibro(copialibro);
                    }

                    if (datiAggiornatDTO.getUtenteId() != null) {
                        Utente utente = utenteRepository.findById(datiAggiornatDTO.getUtenteId())
                                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con id: " + datiAggiornatDTO.getUtenteId()));
                        prestitoEsistente.setUtente(utente);
                    }

                    Prestito prestitoAggiornato = prestitoRepository.save(prestitoEsistente);
                    return prestitoMapper.toDto(prestitoAggiornato);
                });
    }

}
