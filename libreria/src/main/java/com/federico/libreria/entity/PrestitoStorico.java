package com.federico.libreria.entity; // O in un package dedicato come .storico

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

@Table("prestito_storico")
@Getter
@Setter
public class PrestitoStorico {

    // Partitioned Key
    @PrimaryKeyColumn(name = "id_prestito", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long idPrestito;

    // Clustering Column
    @PrimaryKeyColumn(name = "data_evento", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant dataEvento;

    @Column("tipo_evento")
    private String tipoEvento;

    @Column("id_copialibro")
    private Long idCopialibro;

    @Column("id_utente")
    private Long idUtente;

    @Column("data_prestito")
    private Instant dataPrestito;

    @Column("data_restituzione")
    private Instant dataRestituzione;
}