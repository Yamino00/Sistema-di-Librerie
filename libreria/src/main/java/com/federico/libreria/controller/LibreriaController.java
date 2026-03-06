package com.federico.libreria.controller;

import com.federico.libreria.dto.LibreriaDTO;
import com.federico.libreria.service.LibreriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libreria")
public class LibreriaController implements LibreriaApiDoc {

    private final LibreriaService libreriaService;

    public LibreriaController(LibreriaService libreriaService) {
        this.libreriaService = libreriaService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LibreriaDTO>> getTuttiLibreria() {
        List<LibreriaDTO> libreria = libreriaService.findAllLibreria();
        return ResponseEntity.ok(libreria);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LibreriaDTO> getLibreriaPerId(@PathVariable Long id) {
        return libreriaService.findLibreriaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<LibreriaDTO> postLibreria(@RequestBody LibreriaDTO nuovaLibreriaDTO) {
        LibreriaDTO libreriaSalvata = libreriaService.save(nuovaLibreriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(libreriaSalvata);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<LibreriaDTO> putLibreria(@PathVariable Long id, @RequestBody LibreriaDTO datiAggiornatiDTO) {
        return libreriaService.updateLibreria(id, datiAggiornatiDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibreria(@PathVariable Long id) {
        if (libreriaService.existsById(id)) {
            libreriaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
