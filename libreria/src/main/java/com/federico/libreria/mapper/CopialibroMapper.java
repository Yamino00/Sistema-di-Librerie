package com.federico.libreria.mapper;

import com.federico.libreria.dto.CopialibroDTO;
import com.federico.libreria.entity.Copialibro;
import com.federico.libreria.entity.Libreria;
import com.federico.libreria.entity.Libro;
import org.springframework.stereotype.Component;

@Component
public class CopialibroMapper {

    // DA ENTITY A DTO (Lettura dal DB)
    public CopialibroDTO toDto(Copialibro entity) {
        if (entity == null) return null;

        CopialibroDTO dto = new CopialibroDTO();
        dto.setId(entity.getId());
        dto.setScaffale(entity.getScaffale());
        dto.setRipiano(entity.getRipiano());
        // Estraggo i dati dal Libro collegato (se esiste)
        if (entity.getLibro() != null) {
            dto.setLibroId(entity.getLibro().getId());
            dto.setNomeLibro(entity.getLibro().getNomeLibro());
        }
        // Estraggo i dati dalla Libreria collegata (se esiste)
        if (entity.getLibreria() != null) {
            dto.setLibreriaId(entity.getLibreria().getId());
            dto.setNomeLibreria(entity.getLibreria().getNome());
        }
        return dto;
    }
    // DA DTO A ENTITY (Salvataggio nel DB)
    public Copialibro toEntity(CopialibroDTO dto) {
        if (dto == null) return null;

        Copialibro entity = new Copialibro();
        entity.setScaffale(dto.getScaffale());
        entity.setRipiano(dto.getRipiano());

        // RICOSTRUIRE LA RELAZIONE
        // Qui facciamo un trucco standard: creiamo un oggetto "finto"
        // con solo l'ID. Hibernate capirà che deve collegarsi a quella riga.

        if (dto.getLibroId() != null) {
            Libro libroStub = new Libro();
            libroStub.setId(dto.getLibroId());
            entity.setLibro(libroStub);
        }

        if (dto.getLibreriaId() != null) {
            Libreria libreriaStub = new Libreria();
            libreriaStub.setId(dto.getLibreriaId());
            entity.setLibreria(libreriaStub);
        }

        return entity;
    }
}