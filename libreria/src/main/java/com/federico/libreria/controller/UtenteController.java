package com.federico.libreria.controller;

import com.federico.libreria.dto.UtenteDTO;
import com.federico.libreria.service.UtenteService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utente")
public class UtenteController implements UtenteApiDoc {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UtenteDTO>> getTuttiUtente() {
        List<UtenteDTO> utente = utenteService.findAllUtente();
        return ResponseEntity.ok(utente);
    }

    @Override
    @GetMapping("/tuttiidUtente")
    public ResponseEntity<List<Long>> getIdUtenti() {
        List<Long> idUtente = utenteService.findAllUtenteId();
        return ResponseEntity.ok(idUtente);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UtenteDTO> getUtentePerId(@PathVariable Long id) {
        return utenteService.findUtenteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/email/{email}")
    public ResponseEntity<UtenteDTO> getByUtentePerEmail(@PathVariable String email) {
        return utenteService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<UtenteDTO> postUtente(@RequestBody UtenteDTO utenteDto) {
        UtenteDTO utenteSalvato = utenteService.save(utenteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(utenteSalvato);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UtenteDTO> putUtente(@PathVariable Long id, @RequestBody UtenteDTO datiAggiornatiDTO) {
        return utenteService.updateUtente(id, datiAggiornatiDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable Long id) {
        if (utenteService.existsById(id)) {
            utenteService.deleteByUtenteId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> gestioneErroreDB(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Impossibile eliminare l'utente: esistono prestiti collegati a questo ID.");
    }
}
