package com.federico.libreria.controller;

import com.federico.libreria.dto.PrestitoDTO;
import com.federico.libreria.service.PrestitoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestito")
public class PrestitoController implements PrestitoApiDoc {
    private final PrestitoService prestitoService;

    public PrestitoController(PrestitoService prestitoService) {
        this.prestitoService = prestitoService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PrestitoDTO>> getTuttiPrestito() {
        List<PrestitoDTO> prestito = prestitoService.findAllPrestito();
        return ResponseEntity.ok(prestito);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PrestitoDTO> getPrestitoPerId(@PathVariable Long id) {
        return prestitoService.findPrestitoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<PrestitoDTO> postPrestito(@RequestBody PrestitoDTO nuovoPrestitoDTO) {
        PrestitoDTO prestitoSalvato = prestitoService.save(nuovoPrestitoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestitoSalvato);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PrestitoDTO> putPrestito(@PathVariable Long id, @RequestBody PrestitoDTO datiAggiornatDTO) {
        return prestitoService.updatePrestito(id, datiAggiornatDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestito(@PathVariable Long id) {
        if (prestitoService.existsById(id)) {
            prestitoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
