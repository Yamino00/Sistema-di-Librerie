package com.federico.libreria.mapper;

import com.federico.libreria.dto.CopialibroDTO;
import com.federico.libreria.entity.Copialibro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CopialibroMapper {

    @Mapping(source = "libro.id", target = "libroId")
    @Mapping(source = "libro.nomeLibro", target = "nomeLibro")
    @Mapping(source = "libreria.id", target = "libreriaId")
    @Mapping(source = "libreria.nome", target = "nomeLibreria")
    CopialibroDTO toDto(Copialibro entity);

    @Mapping(source = "libroId", target = "libro.id")
    @Mapping(source = "libreriaId", target = "libreria.id")
    Copialibro toEntity(CopialibroDTO dto);
}