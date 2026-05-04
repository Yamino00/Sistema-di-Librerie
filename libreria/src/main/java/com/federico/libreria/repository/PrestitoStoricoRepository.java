package com.federico.libreria.repository;

import com.federico.libreria.entity.PrestitoStorico;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestitoStoricoRepository extends CassandraRepository<PrestitoStorico, Long> {
}
