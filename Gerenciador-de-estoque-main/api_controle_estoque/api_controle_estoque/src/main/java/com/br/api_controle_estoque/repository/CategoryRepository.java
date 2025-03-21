package com.br.api_controle_estoque.repository;

import com.br.api_controle_estoque.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findByNameContainingIgnoreCase(String name);
}
