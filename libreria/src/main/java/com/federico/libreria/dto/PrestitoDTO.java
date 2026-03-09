package com.federico.libreria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PrestitoDTO {
    private Long id;
    private Long copialibroId;
    private String nomeLibro;
    private Long utenteId;
    private String nomeUtente;
    private Date dataPrestito;
    private Date dataRestituzione;
}
