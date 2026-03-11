package com.federico.libreria.mapper;

import com.federico.libreria.dto.LibreriaDTO;
import com.federico.libreria.entity.Libreria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LibreriaMapper {
    LibreriaDTO toDto(Libreria entity);

    Libreria toEntity(LibreriaDTO dto);
}