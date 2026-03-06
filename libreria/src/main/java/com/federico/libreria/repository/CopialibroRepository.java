package com.federico.libreria.repository;

import com.federico.libreria.entity.Copialibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopialibroRepository extends JpaRepository<Copialibro, Long> {
}
