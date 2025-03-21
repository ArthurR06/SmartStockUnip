package com.br.api_controle_estoque.DTO.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdressRequestDto(
        @NotNull
        @Pattern(regexp = "\\d{8}", message = "The CEP must contain 8 numeric digits")
        String cep,

        @Column(name = "logradouro")
        @NotNull
        String public_place,

        @Column(name = "numero")
        @NotNull
        @Size(max = 10, message = "The number must have a maximum of 10 characters.")
        String number,

        @Column(name = "bairro")
        @NotNull
        String neighborhood,

        @Column(name = "cidade")
        @NotNull
        String city,

        @Column(name = "estado")
        @NotNull
        String state
) {
}
