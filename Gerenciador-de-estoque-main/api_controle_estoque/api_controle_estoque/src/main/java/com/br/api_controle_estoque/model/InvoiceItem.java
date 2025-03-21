package com.br.api_controle_estoque.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_nota_fiscal")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nota")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Product product;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal unitPrice;

    public InvoiceItem() {}

    public InvoiceItem(Invoice invoice, Product product, int quantity, BigDecimal unitPrice) {
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (this.invoice != null) {
            this.invoice.updateTotalAmount(); // Atualiza total ao modificar a quantidade
        }
    }

    public void setPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        if (this.invoice != null) {
            this.invoice.updateTotalAmount(); // Atualiza total ao modificar o preço
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
