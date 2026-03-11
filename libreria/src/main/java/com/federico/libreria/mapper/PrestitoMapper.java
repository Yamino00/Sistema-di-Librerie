package com.federico.libreria.mapper;

import com.federico.libreria.dto.PrestitoDTO;
import com.federico.libreria.entity.Prestito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrestitoMapper {

    @Mapping(source = "copialibro.id", target = "copialibroId")
    @Mapping(source = "copialibro.libro.nomeLibro", target = "nomeLibro")
    @Mapping(source = "utente.id", target = "utenteId")
    @Mapping(target = "nomeUtente", expression = "java(entity.getUtente() != null ? entity.getUtente().getNome() + \" \" + entity.getUtente().getCognome() : null)")
    PrestitoDTO toDto(Prestito entity);

    @Mapping(source = "copialibroId", target = "copialibro.id")
    @Mapping(source = "utenteId", target = "utente.id")
    Prestito toEntity(PrestitoDTO dto);
}