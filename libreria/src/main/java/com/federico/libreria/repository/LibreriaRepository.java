package com.federico.libreria.repository;

import com.federico.libreria.entity.Libreria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibreriaRepository extends JpaRepository<Libreria, Long> {
}
