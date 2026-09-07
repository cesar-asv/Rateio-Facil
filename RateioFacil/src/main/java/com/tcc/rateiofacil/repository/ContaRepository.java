package com.tcc.rateiofacil.repository;

import com.tcc.rateiofacil.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}