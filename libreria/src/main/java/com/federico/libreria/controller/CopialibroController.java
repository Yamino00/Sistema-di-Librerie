package com.federico.libreria.controller;

import com.federico.libreria.dto.CopialibroDTO;
import com.federico.libreria.service.CopialibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/copialibro")
public class CopialibroController implements CopialibroApiDoc {

    private final CopialibroService copialibroService;

    public CopialibroController(CopialibroService copialibroService) {
        this.copialibroService = copialibroService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CopialibroDTO>> getTuttiCopialibro() {
        List<CopialibroDTO> copialibro = copialibroService.findAllCopialibro();
        return ResponseEntity.ok(copialibro);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CopialibroDTO> getCopialibroPerId(@PathVariable Long id) {
        return copialibroService.findCopialibroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<CopialibroDTO> postCopialibro(@RequestBody CopialibroDTO nuovoCopialibroDTO) {
        CopialibroDTO copialibroSalvato = copialibroService.save(nuovoCopialibroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(copialibroSalvato);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CopialibroDTO> putCopialibro(@PathVariable Long id, @RequestBody CopialibroDTO datiAggiornatiDTO) {
        return copialibroService.updateCopialibro(id, datiAggiornatiDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCopialibro(@PathVariable Long id) {
        if (copialibroService.existsById(id)) {
            copialibroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
