package com.federico.libreria.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "libreria")
@Getter
@Setter
public class LibreriaDocument {
    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String nome;

    @Field(type = FieldType.Text)
    private String indirizzo;

    @Field(type = FieldType.Text)
    private String citta;

}
