package com.br.api_controle_estoque.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao_saida")
public class StockOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_saida")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Product product;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Column(name = "data_saida", nullable = false, updatable = false)
    private LocalDateTime outputDate = LocalDateTime.now();

    @Column(name = "observacao")
    private String observation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(LocalDateTime outputDate) {
        this.outputDate = outputDate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
