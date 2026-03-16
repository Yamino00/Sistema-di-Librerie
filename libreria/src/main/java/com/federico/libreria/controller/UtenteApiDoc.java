package com.federico.libreria.controller;

import com.federico.libreria.dto.UtenteDTO;
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
        name = "Documentazione API correlata agli utenti",
        description = "API REST complete per la gestione degli utenti " + "Tutti gli endpoint supportano operazioni CRUD (Create, Read, Update, Delete) standard."
)

public interface UtenteApiDoc {
    // ------------------------
    // UTENTE API
    // ------------------------

    @Operation(
            summary = "Recupera tutti gli utenti",
            description = """
                    Restituisce l'elenco completo di tutti gli utenti registrati nel sistema.
                    Che contengono i seguenti campi:
                    - id: Long (generato automaticamente)
                    - nome: String (obbligatorio)
                    - cognome: String (obbligatorio)
                    - email: String (obbligatorio)
                    - eta: Integer (obbligatorio)
                    - genere: String (obbligatorio)
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista di utenti recuperata con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    [
                                      {
                                        "id": 1,
                                        "nome": "Federico",
                                        "cognome": "Leccese",
                                        "email": "federicoeccese335@gmail.com",
                                        "eta": 30,
                                        "genere": "M"
                                      }
                                    ]
                                    """
                    )
            )
    )
    @GetMapping("/utente")
    ResponseEntity<List<UtenteDTO>> getTuttiUtente();

    @Operation(summary = "Recupera tutti gli ID utente")
    @ApiResponse(
            responseCode = "200",
            description = "Utenti ID trovati",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    [
                                        1,
                                        2,
                                        3,
                                        4,
                                        5,
                                        6,
                                        7,
                                        8,
                                        9,
                                        10,
                                        11,
                                        12,
                                        13
                                    ]
                                    """
                    )
            )
    )
    @ApiResponse(responseCode = "404", description = "Utente ID non trovati",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    []
                                    """
                    )
            )
    )
    @GetMapping("/tuttiidUtente")
    ResponseEntity<List<Long>> getIdUtenti();

    @Operation(summary = "Recupera un utente per ID")
    @ApiResponse(responseCode = "200", description = "Utente trovato")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    @GetMapping("/{id}")
    ResponseEntity<UtenteDTO> getUtentePerId(
            @PathVariable("id") @Parameter(description = "ID univoco dell'utente", example = "1") Long id
    );

    @Operation(
            summary = "Cerca utenti per email",
            description = "Restituisce gli utenti che corrispondono all'email specificata."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Utenti trovati",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    [
                                      {
                                        "id": 1,
                                        "nome": "Federico",
                                        "cognome": "Leccese",
                                        "email": "Federicoeccese335@gmail.com",
                                        "eta": 30,
                                        "genere": "M"
                                      }
                                    ]
                                    """
                    )
            )
    )
    @ApiResponse(responseCode = "404", description = "Nessun utente trovato con questa email")
    @GetMapping("/email/{email}")
    ResponseEntity<UtenteDTO> getByUtentePerEmail(
            @PathVariable("email")
            @Parameter(description = "Email dell'utente da cercare", example = "Federicoeccese335@gmail.com")
            String email
    );

    @Operation(
            summary = "Crea un nuovo utente",
            description = "Registra un nuovo utente nel sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Utente creato con successo",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "id": 2,
                                      "nome": "Laura",
                                      "cognome": "Bianchi",
                                      "email": "laura.bianchi@example.com",
                                      "eta": 25,
                                      "genere": "F"
                                    }
                                    """
                    )
            )
    )
    @PostMapping
    ResponseEntity<UtenteDTO> postUtente(
            @RequestBody UtenteDTO nuovoUtente
    );

    @Operation(summary = "Aggiorna un utente esistente")
    @ApiResponse(responseCode = "200", description = "Utente aggiornato con successo")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    @PutMapping("/{id}")
    ResponseEntity<UtenteDTO> putUtente(
            @PathVariable("id") Long id,
            @RequestBody UtenteDTO datiAggiornati
    );

    @Operation(summary = "Elimina un utente")
    @ApiResponse(responseCode = "204", description = "Utente eliminato con successo")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUtente(@PathVariable("id") Long id);
}
