package com.tcc.rateiofacil.repository;

import com.tcc.rateiofacil.model.Item;
import com.tcc.rateiofacil.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT DISTINCT i FROM Item i JOIN FETCH i.conta JOIN FETCH i.participantes " +
            "WHERE :participante MEMBER OF i.participantes")
    List<Item> findByParticipante(@Param("participante") Participante participante);
}