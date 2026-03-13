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
             SELECT c\s
             FROM Copialibro c\s
             WHERE c.id NOT IN (
                 SELECT p.copialibro.id\s
                 FROM Prestito p\s
                 WHERE p.dataRestituzione IS NULL
             )
            \s""")
    List<Copialibro> findTutteCopieDisponibili();
}
