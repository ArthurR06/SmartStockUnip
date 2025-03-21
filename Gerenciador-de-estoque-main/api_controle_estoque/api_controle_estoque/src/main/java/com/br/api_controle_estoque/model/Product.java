package com.br.api_controle_estoque.model;

import com.br.api_controle_estoque.model.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "produto")
public class Product {

    @Id
    @Column(name = "id_produto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "The name of the product is required.")
    @Column(name = "nome")
    private String name;
    @Column(name = "descricao")
    private String description;

    @NotNull
    @Column(name = "localizacao")
    private String location;

    @Column(name = "quantidade_estoque")
    private Integer quantity_stock = 0;

    @Column(name = "quantidade_minima")
    @NotNull
    @Positive
    private Integer quantity_min;

    @Column(name = "unidade_de_medida")
    @NotNull
    @Size(max = 3)
    private String unit_of_measure;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    @NotNull
    private Category category;

    @OneToMany(mappedBy = "product",  cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StockOutput> stockOutputs;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> invoiceItems;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public @NotEmpty(message = "The name of the product is required.") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name of the product is required.") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity_stock() {
        return quantity_stock;
    }

    public void setQuantity_stock(Integer quantity_stock) {
        this.quantity_stock = quantity_stock;
    }

    public @NotNull @Positive Integer getQuantity_min() {
        return quantity_min;
    }

    public void setQuantity_min(@NotNull @Positive Integer quantity_min) {
        this.quantity_min = quantity_min;
    }

    public @NotNull String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(@NotNull String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }

    public @NotNull Status getStatus() {
        return status;
    }

    public void setStatus(@NotNull Status status) {
        this.status = status;
    }

    public @NotNull String getLocation() {
        return location;
    }

    public void setLocation(@NotNull String location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<StockOutput> getStockOutputs() {
        return stockOutputs;
    }

    public void setStockOutputs(List<StockOutput> stockOutputs) {
        this.stockOutputs = stockOutputs;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
