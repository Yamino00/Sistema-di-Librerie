package com.federico.libreria.repository;

import com.federico.libreria.entity.CopialibroDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopialibroDocumentRepository extends ElasticsearchRepository<CopialibroDocument, Long> {
}
