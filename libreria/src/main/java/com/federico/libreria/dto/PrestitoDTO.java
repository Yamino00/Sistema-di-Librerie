package com.federico.libreria.dto;

import java.util.Date;

public class PrestitoDTO {
    private Long id;
    private Long copialibroId;
    private String nomeLibro;
    private Long utenteId;
    private String nomeUtente;
    private Date dataPrestito;
    private Date dataRestituzione;

    public PrestitoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCopialibroId() {
        return copialibroId;
    }

    public void setCopialibroId(Long copialibroId) {
        this.copialibroId = copialibroId;
    }

    public String getNomeLibro() {
        return nomeLibro;
    }

    public void setNomeLibro(String nomeLibro) {
        this.nomeLibro = nomeLibro;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public Date getDataPrestito() {
        return dataPrestito;
    }

    public void setDataPrestito(Date dataPrestito) {
        this.dataPrestito = dataPrestito;
    }

    public Date getDataRestituzione() {
        return dataRestituzione;
    }

    public void setDataRestituzione(Date dataRestituzione) {
        this.dataRestituzione = dataRestituzione;
    }
}
