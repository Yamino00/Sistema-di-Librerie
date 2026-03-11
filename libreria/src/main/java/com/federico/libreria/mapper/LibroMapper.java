package com.federico.libreria.mapper;

import com.federico.libreria.dto.LibroDTO;
import com.federico.libreria.entity.Libro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LibroMapper {
    LibroDTO toDto(Libro entity);

    Libro toEntity(LibroDTO dto);
}