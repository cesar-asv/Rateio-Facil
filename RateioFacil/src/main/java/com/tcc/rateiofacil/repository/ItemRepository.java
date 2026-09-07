package com.tcc.rateiofacil.repository;

import com.tcc.rateiofacil.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}