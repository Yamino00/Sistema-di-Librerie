package com.federico.libreria.mapper;

import com.federico.libreria.document.LibroDocument;
import com.federico.libreria.dto.LibroDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LibroDocumentMapper {

    LibroDocument toDocument(LibroDTO dto);

}
