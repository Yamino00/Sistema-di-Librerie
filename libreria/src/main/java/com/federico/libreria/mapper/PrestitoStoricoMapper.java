package com.federico.libreria.mapper;

import com.federico.libreria.entity.Prestito;
import com.federico.libreria.entity.PrestitoStorico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrestitoStoricoMapper {

    @Mapping(source = "id", target = "idPrestito")
    @Mapping(source = "copialibro.id", target = "idCopialibro")
    @Mapping(source = "utente.id", target = "idUtente")
    @Mapping(target = "dataEvento", expression = "java(java.time.Instant.now())")
    @Mapping(target = "tipoEvento", constant = "ARCHIVIAZIONE_STORICO")
    @Mapping(target = "dataPrestito", expression = "java(prestito.getDataPrestito() != null ? prestito.getDataPrestito().toInstant() : null)")
    @Mapping(target = "dataRestituzione", expression = "java(prestito.getDataRestituzione() != null ? prestito.getDataRestituzione().toInstant() : null)")
    PrestitoStorico toStorico(Prestito prestito);
}