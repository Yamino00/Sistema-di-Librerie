package com.federico.libreria.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "utente")
@Getter
@Setter
public class UtenteDocument {

    private Long id;

    @Field(type = FieldType.Text)
    private String nome;

    @Field(type = FieldType.Text)
    private String cognome;

    @Field(type = FieldType.Text)
    private String email;

    @Field(type = FieldType.Integer)
    private Integer eta;

    @Field(type = FieldType.Keyword)
    private String genere;
}
