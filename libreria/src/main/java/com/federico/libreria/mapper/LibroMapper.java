package com.federico.libreria.mapper;

import com.federico.libreria.dto.LibroDTO;
import com.federico.libreria.entity.Libro;
import org.springframework.stereotype.Component;

@Component
public class LibroMapper {

    public LibroDTO toDto(Libro entity) {
        if (entity == null) return null;

        LibroDTO dto = new LibroDTO();
        dto.setId(entity.getId());
        dto.setNomeLibro(entity.getNomeLibro());
        dto.setTrama(entity.getTrama());
        dto.setAnno(entity.getAnno());
        dto.setGenere(entity.getGenere());
        dto.setAutore(entity.getAutore());

        return dto;
    }

    public Libro toEntity(LibroDTO dto) {
        if (dto == null) return null;

        Libro entity = new Libro();
        entity.setNomeLibro(dto.getNomeLibro());
        entity.setTrama(dto.getTrama());
        entity.setAnno(dto.getAnno());
        entity.setGenere(dto.getGenere());
        entity.setAutore(dto.getAutore());

        return entity;
    }
}
