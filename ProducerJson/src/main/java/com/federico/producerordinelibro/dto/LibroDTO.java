package com.federico.producerordinelibro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class LibroDTO {
    private Long id;

    private String nomeLibro;

    private String trama;

    private Integer anno;

    private String genere;

    private String autore;
}
