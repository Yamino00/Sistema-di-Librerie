package com.federico.libreria.repository;

import com.federico.libreria.entity.LibreriaDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibreriaDocumentRepository extends ElasticsearchRepository<LibreriaDocument, Integer> {
}
