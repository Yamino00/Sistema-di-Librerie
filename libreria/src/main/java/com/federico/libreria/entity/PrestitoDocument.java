package com.federico.libreria.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;

@Document(indexName = "prestito")
@Getter
@Setter
public class PrestitoDocument {

    private Long id;

    @Field(type = FieldType.Long)
    private Long idUtente;

    @Field(type = FieldType.Long)
    private Long idCopialibro;

    @Field(type = FieldType.Date)
    private Timestamp dataInizio;

    @Field(type = FieldType.Date)
    private Timestamp dataFine;
}
