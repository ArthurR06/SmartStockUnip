package com.br.api_controle_estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "The name of the user is required.")
    @Column(name = "nome")
    private String name;
    @NotNull(message = "The email of the user is required.")
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    @NotNull(message = "The password of the user is required.")
    @Column(name = "senha")
    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "The name of the user is required.") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name of the user is required.") String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
