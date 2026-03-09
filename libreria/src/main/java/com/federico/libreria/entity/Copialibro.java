package com.federico.libreria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "copialibro")
@Getter @Setter
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

}
