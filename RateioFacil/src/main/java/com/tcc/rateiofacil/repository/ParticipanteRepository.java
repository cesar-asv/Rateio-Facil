package com.tcc.rateiofacil.repository;

import com.tcc.rateiofacil.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    @Query("SELECT p FROM Participante p WHERE " +
            "(:nome IS NULL OR :nome = '' OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "AND " +
            "(:email IS NULL OR :email = '' OR LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<Participante> buscar(@Param("nome") String nome, @Param("email") String email);
}
