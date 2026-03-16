package com.federico.libreria.repository;

import com.federico.libreria.entity.Copialibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CopialibroRepository extends JpaRepository<Copialibro, Long> {
    // Trova tutte le copie fisiche disponibili (non in prestito)
    @Query(value = """
            SELECT c.*
            FROM Copialibro c
            WHERE c.id NOT IN (
                SELECT p.id_copialibro
                FROM Prestito p
                WHERE p.data_restituzione IS NULL
             )
            """, nativeQuery = true)
    List<Copialibro> findTutteCopieDisponibili();
}
