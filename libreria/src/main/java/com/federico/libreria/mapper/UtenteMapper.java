package com.federico.libreria.mapper;

import com.federico.libreria.dto.UtenteDTO;
import com.federico.libreria.entity.Utente;
import org.springframework.stereotype.Component;

@Component
public class UtenteMapper {

    public UtenteDTO toDto(Utente entity) {
        if (entity == null) return null;

        UtenteDTO dto = new UtenteDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCognome(entity.getCognome());
        dto.setEmail(entity.getEmail());
        dto.setEta(entity.getEta());
        dto.setGenere(entity.getGenere());

        return dto;
    }

    public Utente toEntity(UtenteDTO dto) {
        if (dto == null) return null;

        Utente entity = new Utente();
        entity.setNome(dto.getNome());
        entity.setCognome(dto.getCognome());
        entity.setEmail(dto.getEmail());
        entity.setEta(dto.getEta());
        entity.setGenere(dto.getGenere());

        return entity;
    }
}
