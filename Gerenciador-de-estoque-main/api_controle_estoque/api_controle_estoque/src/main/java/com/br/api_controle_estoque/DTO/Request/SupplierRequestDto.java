package com.br.api_controle_estoque.DTO.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record SupplierRequestDto(
        @NotEmpty(message = "The name of the supplier is required.")
        String name,

        @Pattern(regexp = "\\d{10,11}", message = "The phone number must contain 10 or 11 numeric digits")
        String phone,
        @Email
        String email,

        @NotNull
        @Pattern(regexp = "\\d{14}", message = "The CNPJ must contain 14 numeric digits")
        String cnpj,

        @NotNull
        Long adressId
) {
}
