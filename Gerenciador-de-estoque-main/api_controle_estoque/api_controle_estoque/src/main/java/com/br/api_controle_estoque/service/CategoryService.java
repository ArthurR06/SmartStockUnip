package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Response.CategoryResponseDTO;
import com.br.api_controle_estoque.exceptions.NotFoundException;
import com.br.api_controle_estoque.model.Category;
import com.br.api_controle_estoque.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;



    public List<Category> listCategory(){
        return categoryRepository.findAll(Sort.by("name").ascending());
    }

    public Category saveCategory(Category category){

        return categoryRepository.save(category);
    }

    public List<CategoryResponseDTO> searchCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("O nome não pode ser vazio.");
        }

        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);


        if (categories.isEmpty()) {
            throw new RuntimeException("Nenhuma categoria encontrada.");
        }

        return categories.stream()
                .map(CategoryResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Category searchCategory(Long id){

        return categoryRepository.findById(id).orElseThrow( () -> new NotFoundException("Nenhuma categoria encontrada com esse id"));
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
