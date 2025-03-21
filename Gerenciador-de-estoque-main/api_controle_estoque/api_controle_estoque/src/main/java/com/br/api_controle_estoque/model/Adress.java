package com.br.api_controle_estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "endereco")
public class Adress {
    @Id
    @Column(name = "id_endereco")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "\\d{8}", message = "The CEP must contain 8 numeric digits")
    private String cep;

    @Column(name = "logradouro")
    @NotNull
    private String public_place;

    @Column(name = "numero")
    @NotNull
    @Size(max = 10, message = "The number must have a maximum of 10 characters.")
    private String number;

    @Column(name = "bairro")
    @NotNull
    private String neighborhood;

    @Column(name = "cidade")
    @NotNull
    private String city;

    @Column(name = "estado")
    @NotNull
    private String state;

    @OneToOne
    @JoinColumn(name = "id_fornecedor", unique = true)
    private Supplier supplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Pattern(regexp = "\\d{8}", message = "The CEP must contain 8 numeric digits") String getCep() {
        return cep;
    }

    public void setCep(@NotNull @Pattern(regexp = "\\d{8}", message = "The CEP must contain 8 numeric digits") String cep) {
        this.cep = cep;
    }

    public @NotNull String getPublic_place() {
        return public_place;
    }

    public void setPublic_place(@NotNull String public_place) {
        this.public_place = public_place;
    }

    public @NotNull @Size(max = 10, message = "The number must have a maximum of 10 characters.") String getNumber() {
        return number;
    }

    public void setNumber(@NotNull @Size(max = 10, message = "The number must have a maximum of 10 characters.") String number) {
        this.number = number;
    }

    public @NotNull String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(@NotNull String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public @NotNull String getCity() {
        return city;
    }

    public void setCity(@NotNull String city) {
        this.city = city;
    }

    public @NotNull String getState() {
        return state;
    }

    public void setState(@NotNull String state) {
        this.state = state;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
