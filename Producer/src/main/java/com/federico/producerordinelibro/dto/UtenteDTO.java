package com.federico.producerordinelibro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UtenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private Integer eta;
    private String genere;
}
