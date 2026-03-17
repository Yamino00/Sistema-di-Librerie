package com.federico.libreria.repository;

import com.federico.libreria.entity.PrestitoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestitoDocumentRepository extends ElasticsearchRepository<PrestitoDocument, Integer> {
}
