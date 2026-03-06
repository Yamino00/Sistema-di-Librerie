package com.federico.libreria.controller;

import com.federico.libreria.dto.LibreriaDTO;
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
        name = "Documentazione API correlata alle librerie",
        description = "API REST complete per la gestione delle librerie" + "Tutti gli endpoint supportano operazioni CRUD (Create, Read, Update, Delete) standard."
)
public interface LibreriaApiDoc {
    // ------------------------
    // LIBRERIA API
    // ------------------------

    @Operation(
            summary = "Recupera tutte le librerie",
            description = """
                    Restituisce l'elenco completo di tutte le librerie registrate nel sistema.
                    Che contengono i seguenti campi:
                    - id: Long (generato automaticamente)
                    - nome: String (obbligatorio)
                    - indirizzo: String (obbligatorio)
                    - citta: String (obbligatorio)
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista di librerie recuperata con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Lista librerie",
                            value = """
                                    [
                                      {
                                        "id": 1,
                                        "nome": "Libreria Bella",
                                        "indirizzo": "Via Marsiglia 123",
                                        "citta": "Milano"
                                      },
                                      {
                                        "id": 2,
                                        "nome": "Libreria Brutta",
                                        "indirizzo": "Corso Vittorio Emanuele 666",
                                        "citta": "Roma"
                                      }
                                    ]
                                    """
                    )
            )
    )
    @GetMapping("/libreria")
    ResponseEntity<List<LibreriaDTO>> getTuttiLibreria();

    @Operation(
            summary = "Recupera una libreria per ID",
            description = "Restituisce i dettagli di una specifica libreria identificata dal suo ID univoco."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Libreria trovata",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "id": 1,
                                      "nome": "Libreria Centrale",
                                      "indirizzo": "Via Roma 123",
                                      "citta": "Milano"
                                    }
                                    """
                    )
            )
    )
    @ApiResponse(responseCode = "404", description = "Libreria non trovata")
    @GetMapping("/libreria/{id}")
    ResponseEntity<LibreriaDTO> getLibreriaPerId(
            @PathVariable("id")
            @Parameter(description = "ID univoco della libreria", example = "1")
            Long id
    );

    @Operation(
            summary = "Crea una nuova libreria",
            description = "Inserisce una nuova libreria nel sistema. L'ID viene generato automaticamente."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Libreria creata con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "id": 3,
                                      "nome": "Libreria Nuova",
                                      "indirizzo": "Via Verdi 45",
                                      "citta": "Napoli"
                                    }
                                    """
                    )
            )
    )
    @PostMapping("/libreria")
    ResponseEntity<LibreriaDTO> postLibreria(
            @RequestBody LibreriaDTO nuovaLibreria
    );

    @Operation(
            summary = "Aggiorna una libreria esistente",
            description = "Modifica i dati di una libreria esistente. Tutti i campi vengono aggiornati."
    )
    @ApiResponse(responseCode = "200", description = "Libreria aggiornata con successo")
    @ApiResponse(responseCode = "404", description = "Libreria non trovata")
    @PutMapping("/libreria/{id}")
    ResponseEntity<LibreriaDTO> putLibreria(
            @PathVariable("id") @Parameter(description = "ID della libreria da aggiornare") Long id,
            @RequestBody @Parameter(description = "Dati aggiornati della libreria") LibreriaDTO datiAggiornati
    );

    @Operation(
            summary = "Elimina una libreria",
            description = "Rimuove permanentemente una libreria dal sistema."
    )
    @ApiResponse(responseCode = "204", description = "Libreria eliminata con successo")
    @ApiResponse(responseCode = "404", description = "Libreria non trovata")
    @DeleteMapping("/libreria/{id}")
    ResponseEntity<Void> deleteLibreria(
            @PathVariable("id") @Parameter(description = "ID della libreria da eliminare") Long id
    );
}
