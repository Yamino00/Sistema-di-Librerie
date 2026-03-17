package com.federico.libreria.repository;

import com.federico.libreria.entity.PrestitoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PrestitoDocumentRepository extends ElasticsearchRepository<PrestitoDocument, Integer> {
}
