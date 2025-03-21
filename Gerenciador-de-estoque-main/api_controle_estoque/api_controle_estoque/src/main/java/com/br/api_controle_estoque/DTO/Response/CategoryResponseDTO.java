package com.br.api_controle_estoque.DTO.Response;

import com.br.api_controle_estoque.model.Category;
import com.br.api_controle_estoque.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryResponseDTO {

    private Long id;
    private String name;
    private String description;
    private List<String> productNames;

    public CategoryResponseDTO(Long id, String name, String description, List<String> productNames) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productNames = productNames;
    }

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.productNames = category.getProducts() != null
                ? category.getProducts().stream().map(Product::getName).collect(Collectors.toList())
                : new ArrayList<>();
    }

    public static CategoryResponseDTO fromEntity(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getProducts() != null
                        ? category.getProducts().stream().map(Product::getName).collect(Collectors.toList())
                        : new ArrayList<>());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
