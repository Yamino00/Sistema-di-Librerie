package com.federico.libreria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "prestito")
@Getter
@Setter
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_copialibro", nullable = false)
    private Copialibro copialibro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @Column(name = "data_prestito", nullable = false)
    private Timestamp dataPrestito;

    @Column(name = "data_restituzione")
    private Timestamp dataRestituzione;
}

