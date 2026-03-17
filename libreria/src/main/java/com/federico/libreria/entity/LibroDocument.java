package com.federico.libreria.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "libro")
@Getter
@Setter
public class LibroDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String nomeLibro;

    @Field(type = FieldType.Text)
    private String autore;

    @Field(type = FieldType.Text)
    private String genere;

    @Field(type = FieldType.Integer)
    private Integer anno;

    @Field(type = FieldType.Text)
    private String trama;

}