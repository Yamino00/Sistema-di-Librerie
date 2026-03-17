package com.federico.libreria.repository;

import com.federico.libreria.entity.UtenteDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UtenteDocumentRepository extends ElasticsearchRepository<UtenteDocument, Integer> {
}
