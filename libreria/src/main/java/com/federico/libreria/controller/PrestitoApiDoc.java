package com.federico.libreria.controller;

import com.federico.libreria.dto.PrestitoDTO;
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
        name = "Documentazione API correlata ai prestiti dei libri",
        description = "API REST complete per la gestione dei prestiti " + "Tutti gli endpoint supportano operazioni CRUD (Create, Read, Update, Delete) standard."
)
public interface PrestitoApiDoc {
    // ------------------------
    // PRESTITO API
    // ------------------------

    @Operation(
            summary = "Recupera tutti i prestiti",
            description = """
                    Restituisce l'elenco di tutti i prestiti di libri registrati nel sistema.
                    Che contengono i seguenti campi:
                    - id: Long (generato automaticamente)
                    - copialibro: Copialibro (riferimento, obbligatorio)
                    - utente: Utente (riferimento, obbligatorio)
                    - dataPrestito: Date (obbligatorio)
                    - dataRestituzione: Date (obbligatorio)
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista di prestiti recuperata con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    [
                                       {
                                       "id": 2,
                                       "copialibroId": 3,
                                       "nomeLibro": "1984",
                                       "utenteId": 2,
                                       "nomeUtente": "Federico Leccese",
                                       "dataPrestito": "2023-11-14T23:00:00.000+00:00",
                                       "dataRestituzione": "2023-11-19T23:00:00.000+00:00"
                                        }
                                    ]
                                    """
                    )
            )
    )
    @GetMapping("/prestito")
    ResponseEntity<List<PrestitoDTO>> getTuttiPrestito();

    @Operation(summary = "Recupera un prestito per ID")
    @ApiResponse(responseCode = "200", description = "Prestito trovato")
    @ApiResponse(responseCode = "404", description = "Prestito non trovato")
    @GetMapping("/{id}")
    ResponseEntity<PrestitoDTO> getPrestitoPerId(
            @PathVariable("id") @Parameter(description = "ID univoco del prestito") Long id
    );

    @Operation(
            summary = "Crea un nuovo prestito",
            description = "Registra un nuovo prestito di un libro a un utente."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Prestito creato con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "id": 2,
                                      "copialibro": { "id": 1 },
                                      "utente": { "id": 1 },
                                      "dataPrestito": "2026-01-26",
                                      "dataRestituzione": "2026-02-10"
                                    }
                                    """
                    )
            )
    )
    @PostMapping
    ResponseEntity<PrestitoDTO> postPrestito(
            @RequestBody PrestitoDTO nuovoPrestito
    );

    @Operation(summary = "Aggiorna un prestito esistente")
    @ApiResponse(responseCode = "200", description = "Prestito aggiornato con successo")
    @ApiResponse(responseCode = "404", description = "Prestito non trovato")
    @PutMapping("/{id}")
    ResponseEntity<PrestitoDTO> putPrestito(
            @PathVariable("id") Long id,
            @RequestBody PrestitoDTO datiAggiornati
    );

    @Operation(summary = "Elimina un prestito")
    @ApiResponse(responseCode = "204", description = "Prestito eliminato con successo")
    @ApiResponse(responseCode = "404", description = "Prestito non trovato")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePrestito(@PathVariable("id") Long id);
}
