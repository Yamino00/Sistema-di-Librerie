package com.federico.libreria.mapper;

import com.federico.libreria.dto.UtenteDTO;
import com.federico.libreria.entity.Utente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
    UtenteDTO toDto(Utente entity);

    Utente toEntity(UtenteDTO dto);
}