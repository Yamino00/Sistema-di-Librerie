package com.federico.libreria.mapper;

import com.federico.libreria.dto.PrestitoDTO;
import com.federico.libreria.entity.Copialibro;
import com.federico.libreria.entity.Prestito;
import com.federico.libreria.entity.Utente;
import org.springframework.stereotype.Component;

@Component
public class PrestitoMapper {

    public PrestitoDTO toDto(Prestito entity) {
        if (entity == null) return null;

        PrestitoDTO dto = new PrestitoDTO();
        dto.setId(entity.getId());
        dto.setDataPrestito(entity.getDataPrestito());
        dto.setDataRestituzione(entity.getDataRestituzione());

        if (entity.getCopialibro() != null) {
            dto.setCopialibroId(entity.getCopialibro().getId());
            if (entity.getCopialibro().getLibro() != null) {
                dto.setNomeLibro(entity.getCopialibro().getLibro().getNomeLibro());
            }
        }

        if (entity.getUtente() != null) {
            dto.setUtenteId(entity.getUtente().getId());
            dto.setNomeUtente(entity.getUtente().getNome() + " " + entity.getUtente().getCognome());
        }

        return dto;
    }

    public Prestito toEntity(PrestitoDTO dto) {
        if (dto == null) return null;

        Prestito entity = new Prestito();
        entity.setDataPrestito(dto.getDataPrestito());
        entity.setDataRestituzione(dto.getDataRestituzione());

        if (dto.getCopialibroId() != null) {
            Copialibro copialibroStub = new Copialibro();
            copialibroStub.setId(dto.getCopialibroId());
            entity.setCopialibro(copialibroStub);
        }

        if (dto.getUtenteId() != null) {
            Utente utenteStub = new Utente();
            utenteStub.setId(dto.getUtenteId());
            entity.setUtente(utenteStub);
        }

        return entity;
    }
}
