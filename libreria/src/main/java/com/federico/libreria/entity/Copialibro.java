package com.federico.libreria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "copialibro")
public class Copialibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libreria", nullable = false)
    private Libreria libreria;

    @Column(name = "scaffale")
    private String scaffale;

    @Column(name = "ripiano")
    private String ripiano;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Libreria getLibreria() {
        return libreria;
    }

    public void setLibreria(Libreria libreria) {
        this.libreria = libreria;
    }

    public String getScaffale() {
        return scaffale;
    }

    public void setScaffale(String scaffale) {
        this.scaffale = scaffale;
    }

    public String getRipiano() {
        return ripiano;
    }

    public void setRipiano(String ripiano) {
        this.ripiano = ripiano;
    }

}
