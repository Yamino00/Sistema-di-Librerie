package com.federico.libreria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "libro")
@Getter
@Setter
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_libro", nullable = false)
    private String nomeLibro;

    @Column(name = "trama", nullable = false)
    private String trama;

    @Column(name = "anno", nullable = false)
    private Integer anno;

    @Column(name = "genere", nullable = false)
    private String genere;

    @Column(name = "autore", nullable = false)
    private String autore;
}
