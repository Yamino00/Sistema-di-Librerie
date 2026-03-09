package com.federico.libreria.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class LibreriaDTO {
    private Long id;
    private String nome;
    private String indirizzo;
    private String citta;
}
