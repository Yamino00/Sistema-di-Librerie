package com.federico.libreria.repository;

import com.federico.libreria.entity.Prestito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestitoRepository extends JpaRepository<Prestito, Long> {
    @Query(value = """
            SELECT id FROM prestito WHERE data_restituzione IS NULL
            """, nativeQuery = true)
    List<Long> findIdPrestitiAttivi();
}
