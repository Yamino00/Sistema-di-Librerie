package com.federico.libreria.controller;

import com.federico.libreria.dto.CopialibroDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Documentazione API correlata alle copie dei libri",
        description = "API REST complete per la gestione delle copie dei libri " + "Tutti gli endpoint supportano operazioni CRUD (Create, Read, Update, Delete) standard."
)
public interface CopialibroApiDoc {
    // ------------------------
    // COPIALIBRO API
    // ------------------------

    @Operation(
            summary = "Recupera tutte le copie dei libri",
            description = """
                    Restituisce l'elenco di tutte le copie fisiche dei libri presenti nelle librerie.
                    Che contengono i seguenti campi:
                    - id: Long (generato automaticamente)
                    - libro: Libro (riferimento, obbligatorio)
                    - libreria: Libreria (riferimento, obbligatorio)
                    - scaffale: String (opzionale)
                    - ripiano: String (opzionale)
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista di copie recuperata con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    [
                                      {
                                        "id": 1,
                                        "libro": { "id": 1, "nomeLibro": "Il Signore degli Anelli" },
                                        "libreria": { "id": 1, "nome": "Libreria Centrale" },
                                        "scaffale": "A3",
                                        "ripiano": "2"
                                      }
                                    ]
                                    """
                    )
            )
    )
    @GetMapping("/copialibro")
    ResponseEntity<List<CopialibroDTO>> getTuttiCopialibro();

    @Operation(summary = "Recupera tutte le copie libro disponibili")
    @ApiResponse(responseCode = "200", description = "Copie libro disponibili recuperate con successo")
    @ApiResponse(responseCode = "404", description = "Copie libro disponibili non trovate")
    @GetMapping("/copiedisponibili")
    ResponseEntity<List<CopialibroDTO>> getTuttiCopiadisponibile();

    @Operation(summary = "Recupera una copia libro per ID")
    @ApiResponse(responseCode = "200", description = "Copia libro trovata")
    @ApiResponse(responseCode = "404", description = "Copia libro non trovata")
    @GetMapping("/copialibro/{id}")
    ResponseEntity<CopialibroDTO> getCopialibroPerId(
            @PathVariable("id") @Parameter(description = "ID univoco della copia libro") Long id
    );

    @Operation(
            summary = "Crea una nuova copia di un libro",
            description = "Registra una nuova copia fisica di un libro in una libreria specifica."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Copia libro creata con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "id": 2,
                                      "libro": { "id": 1 },
                                      "libreria": { "id": 1 },
                                      "scaffale": "B5",
                                      "ripiano": "3"
                                    }
                                    """
                    )
            )
    )
    @PostMapping("/copialibro")
    ResponseEntity<CopialibroDTO> postCopialibro(
            @RequestBody CopialibroDTO nuovaCopialibro
    );

    @Operation(summary = "Aggiorna una copia libro esistente")
    @ApiResponse(responseCode = "200", description = "Copia libro aggiornata con successo")
    @ApiResponse(responseCode = "404", description = "Copia libro non trovata")
    @PutMapping("/copialibro/{id}")
    ResponseEntity<CopialibroDTO> putCopialibro(
            @PathVariable("id") Long id,
            @RequestBody CopialibroDTO datiAggiornati
    );

    @Operation(summary = "Elimina una copia libro")
    @ApiResponse(responseCode = "204", description = "Copia libro eliminata con successo")
    @ApiResponse(responseCode = "404", description = "Copia libro non trovata")
    @DeleteMapping("/copialibro/{id}")
    ResponseEntity<Void> deleteCopialibro(@PathVariable("id") Long id);

}
