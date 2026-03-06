package com.federico.libreria.mapper;

import com.federico.libreria.dto.LibreriaDTO;
import com.federico.libreria.entity.Libreria;
import org.springframework.stereotype.Component;

@Component
public class LibreriaMapper {

    public LibreriaDTO toDto(Libreria entity) {
        if (entity == null) return null;

        LibreriaDTO dto = new LibreriaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setIndirizzo(entity.getIndirizzo());
        dto.setCitta(entity.getCitta());

        return dto;
    }

    public Libreria toEntity(LibreriaDTO dto) {
        if (dto == null) return null;

        Libreria entity = new Libreria();
        entity.setNome(dto.getNome());
        entity.setIndirizzo(dto.getIndirizzo());
        entity.setCitta(dto.getCitta());

        return entity;
    }
}
