package com.federico.libreria.repository;

import com.federico.libreria.entity.UtenteDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteDocumentRepository extends ElasticsearchRepository<UtenteDocument, Integer> {
}
