package com.federico.libreria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CopialibroDTO {
    private Long id;

    @NotBlank(message = "Lo scaffale non può essere vuoto o nullo")
    private String scaffale;

    @NotBlank(message = "Il ripiano non può essere vuoto o nullo")
    private String ripiano;

    @NotNull(message = "L'ID del libro è obbligatorio")
    private Long libroId;

    @NotNull(message = "L'ID della libreria è obbligatorio")
    private Long libreriaId;

    private String nomeLibro;

    private String nomeLibreria;
}
