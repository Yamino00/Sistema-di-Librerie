package com.federico.libreria.controller;

import com.federico.libreria.dto.LibroDTO;
import com.federico.libreria.service.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libro")
public class LibroController implements LibroApiDoc {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LibroDTO>> getTuttiLibro() {
        List<LibroDTO> libro = libroService.findAllLibro();
        return ResponseEntity.ok(libro);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getLibroPerId(@PathVariable Long id) {
        return libroService.findLibroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<LibroDTO> postLibro(@RequestBody LibroDTO nuovoLibroDTO) {
        LibroDTO libroSalvato = libroService.save(nuovoLibroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(libroSalvato);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> putLibro(@PathVariable Long id, @RequestBody LibroDTO datiAggiornatDTO) {
        return libroService.updateLibro(id, datiAggiornatDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        if (libroService.existsById(id)) {
            libroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
