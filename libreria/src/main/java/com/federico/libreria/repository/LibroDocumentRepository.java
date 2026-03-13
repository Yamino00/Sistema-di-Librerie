package com.federico.libreria.repository;

import com.federico.libreria.document.LibroDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroDocumentRepository extends ElasticsearchRepository<LibroDocument, Integer> {
}