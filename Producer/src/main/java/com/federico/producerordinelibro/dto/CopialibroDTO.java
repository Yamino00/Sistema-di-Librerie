package com.federico.producerordinelibro.dto;

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

    private String scaffale;

    private String ripiano;

    private Long libroId;

    private Long libreriaId;

    private String nomeLibro;

    private String nomeLibreria;
}
