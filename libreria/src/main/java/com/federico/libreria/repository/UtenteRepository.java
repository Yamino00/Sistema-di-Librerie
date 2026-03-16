package com.federico.libreria.repository;

import com.federico.libreria.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);

    @Query(value = "SELECT id FROM utente", nativeQuery = true)
    List<Long> findAllId();
}
