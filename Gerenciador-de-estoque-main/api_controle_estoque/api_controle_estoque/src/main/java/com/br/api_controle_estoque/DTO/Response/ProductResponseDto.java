package com.br.api_controle_estoque.DTO.Response;

import com.br.api_controle_estoque.model.Product;

public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private String location;
    private Integer quantity_min;
    private Integer quantity_stock;
    private String unit_of_measure;
    private String nameCategory;



    public ProductResponseDto(Long id, String name, String description,
                              String location, Integer quantity_min,
                              Integer quantity_stock, String unit_of_measure,
                              String nameCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.quantity_min = quantity_min;
        this.quantity_stock = quantity_stock;
        this.unit_of_measure = unit_of_measure;
        this.nameCategory = nameCategory;

    }

    public ProductResponseDto() {
    }

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getLocation(),
                product.getQuantity_min(),
                product.getQuantity_stock(),
                product.getUnit_of_measure(),
                product.getCategory() != null ? product.getCategory().getName() : null // Nome da Categoria
        );

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

    public Integer getQuantity_min() {
        return quantity_min;
    }

    public void setQuantity_min(Integer quantity_min) {
        this.quantity_min = quantity_min;
    }

    public Integer getQuantity_stock() {
        return quantity_stock;
    }

    public void setQuantity_stock(Integer quantity_stock) {
        this.quantity_stock = quantity_stock;
    }

    public String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
