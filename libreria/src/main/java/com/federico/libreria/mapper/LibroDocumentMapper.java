package com.federico.libreria.mapper;

import com.federico.libreria.dto.LibroDTO;
import com.federico.libreria.entity.LibroDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LibroDocumentMapper {

    LibroDocument toDocument(LibroDTO dto);

}
