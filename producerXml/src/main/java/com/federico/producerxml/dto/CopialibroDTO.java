package com.federico.producerxml.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    public CopialibroDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibreriaId() {
        return libreriaId;
    }

    public void setLibreriaId(Long libreriaId) {
        this.libreriaId = libreriaId;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public String getRipiano() {
        return ripiano;
    }

    public void setRipiano(String ripiano) {
        this.ripiano = ripiano;
    }

    public String getNomeLibreria() {
        return nomeLibreria;
    }

    public void setNomeLibreria(String nomeLibreria) {
        this.nomeLibreria = nomeLibreria;
    }

    public String getScaffale() {
        return scaffale;
    }

    public void setScaffale(String scaffale) {
        this.scaffale = scaffale;
    }

    public String getNomeLibro() {
        return nomeLibro;
    }

    public void setNomeLibro(String nomeLibro) {
        this.nomeLibro = nomeLibro;
    }

}
