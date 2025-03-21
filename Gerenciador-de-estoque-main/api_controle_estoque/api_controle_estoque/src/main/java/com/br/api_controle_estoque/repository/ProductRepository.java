package com.br.api_controle_estoque.repository;

import com.br.api_controle_estoque.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByNameContainingIgnoreCase(String name);
}
