package com.br.api_controle_estoque.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "fornecedor")
public class Supplier {

    @Id
    @Column(name = "id_fornecedor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The name of the supplier is required.")
    @Column(name = "nome_fornecedor")
    private String name;

    @Column(unique = true, nullable = false, name = "telefone")
    @Pattern(regexp = "\\d{10,11}", message = "The phone number must contain 10 or 11 numeric digits")
    private String phone;
    @Column(unique = true, nullable = false)
    @Email
    private String email;


    @NotNull
    @Pattern(regexp = "\\d{14}", message = "The CNPJ must contain 14 numeric digits")
    private String cnpj;


    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices;

    @NotNull
    @OneToOne(mappedBy = "supplier", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Adress adress;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "The name of the supplier is required.") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name of the supplier is required.") String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotNull @Size(max = 20, message = "The CNPJ must have a maximum of 20 characters.") String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotNull @Size(max = 20, message = "The CNPJ must have a maximum of 20 characters.") String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotNull Adress getAdress() {
        return adress;
    }

    public void setAdress(@NotNull Adress adress) {
        this.adress = adress;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
