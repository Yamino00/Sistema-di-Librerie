package com.federico.libreria.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "copialibro")
@Getter
@Setter
public class CopialibroDocument {

    private Long id;

    @Field(type = FieldType.Long)
    private Long idLibro;

    @Field(type = FieldType.Long)
    private Long idlibreria;

    @Field(type = FieldType.Text)
    private String scaffale;

    @Field(type = FieldType.Text)
    private String ripiano;
}
