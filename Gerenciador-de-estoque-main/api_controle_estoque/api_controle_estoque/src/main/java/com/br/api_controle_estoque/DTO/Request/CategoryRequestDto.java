package com.br.api_controle_estoque.DTO.Request;

import jakarta.validation.constraints.NotNull;

public record CategoryRequestDto(
        @NotNull(message = "The name of the category is required!")
        String name,
        @NotNull(message = "The description of the category is required!")
        String description) {

        public CategoryRequestDto(@NotNull(message = "The name of the category is required!")
                                  String name, @NotNull(message = "The description of the category is required!")
                                  String description) {
                this.name = name;
                this.description = description;
        }
}
