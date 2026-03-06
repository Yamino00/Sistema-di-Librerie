package com.federico.libreria.repository;

import com.federico.libreria.entity.Prestito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestitoRepository extends JpaRepository<Prestito, Long> {
}
