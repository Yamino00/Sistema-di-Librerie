package com.federico.libreria.controller;

import com.federico.libreria.dto.LibroDTO;
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
        name = "Documentazione API correlata ai libri",
        description = "API REST complete per la gestione dei libri " + "Tutti gli endpoint supportano operazioni CRUD (Create, Read, Update, Delete) standard."
)
public interface LibroApiDoc {
    // ------------------------
    // LIBRO API
    // ------------------------

    @Operation(
            summary = "Recupera tutti i libri",
            description = """
                    Restituisce l'elenco completo di tutti i libri catalogati nel sistema.
                    Che contengono i seguenti campi:
                    - id: Long (generato automaticamente)
                    - nomeLibro: String (obbligatorio)
                    - trama: String (obbligatorio)
                    - anno: Integer (obbligatorio)
                    - genere: String (obbligatorio)
                    - autore: String (obbligatorio)
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista di libri recuperata con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    [
                                      {
                                        "id": 1,
                                        "nomeLibro": "Il Signore degli Anelli",
                                        "trama": "Un'epica avventura fantasy nella Terra di Mezzo",
                                        "anno": 1954,
                                        "genere": "Fantasy",
                                        "autore": "J.R.R. Tolkien"
                                      }
                                    ]
                                    """
                    )
            )
    )
    @GetMapping("/libro")
    ResponseEntity<List<LibroDTO>> getTuttiLibro();

    @Operation(
            summary = "Recupera un libro per ID",
            description = "Restituisce i dettagli completi di un libro specifico."
    )
    @ApiResponse(responseCode = "200", description = "Libro trovato")
    @ApiResponse(responseCode = "404", description = "Libro non trovato")
    @GetMapping("/libro/{id}")
    ResponseEntity<LibroDTO> getLibroPerId(
            @PathVariable("id") @Parameter(description = "ID univoco del libro", example = "1") Long id
    );

    @Operation(
            summary = "Crea un nuovo libro",
            description = "Inserisce un nuovo libro nel catalogo. L'ID viene generato automaticamente."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Libro creato con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "id": 2,
                                      "nomeLibro": "1984",
                                      "trama": "Un romanzo distopico ambientato in un futuro totalitario",
                                      "anno": 1949,
                                      "genere": "Distopia",
                                      "autore": "George Orwell"
                                    }
                                    """
                    )
            )
    )
    @PostMapping("/libro")
    ResponseEntity<LibroDTO> postLibro(
            @RequestBody LibroDTO nuovoLibro
    );

    @Operation(summary = "Aggiorna un libro esistente")
    @ApiResponse(responseCode = "200", description = "Libro aggiornato con successo")
    @ApiResponse(responseCode = "404", description = "Libro non trovato")
    @PutMapping("/libro/{id}")
    ResponseEntity<LibroDTO> putLibro(
            @PathVariable("id") Long id,
            @RequestBody LibroDTO datiAggiornati
    );

    @Operation(summary = "Elimina un libro")
    @ApiResponse(responseCode = "204", description = "Libro eliminato con successo")
    @ApiResponse(responseCode = "404", description = "Libro non trovato")
    @DeleteMapping("/libro/{id}")
    ResponseEntity<Void> deleteLibro(@PathVariable("id") Long id);
}
