package com.br.api_controle_estoque.repository;

import com.br.api_controle_estoque.model.StockOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOutputRepository extends JpaRepository<StockOutput, Long> {
}
