package com.br.api_controle_estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "categoria")
public class Category {

    @Id
    @Column(name = "id_categoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name of the category is required!")
    @Column(name = "nome")
    private String name;
    @NotNull(message = "The description of the category is required!")
    @Column(name = "descricao")
    private String description;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "The name of the category is required!") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name of the category is required!") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "The description of the category is required!") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "The description of the category is required!") String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
