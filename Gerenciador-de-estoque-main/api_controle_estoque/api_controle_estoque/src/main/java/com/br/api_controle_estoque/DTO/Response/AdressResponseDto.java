package com.br.api_controle_estoque.DTO.Response;

import com.br.api_controle_estoque.model.Adress;

public record AdressResponseDto(
        Long id,
        String cep,
        String public_place,
        String number,
        String neighborhood,
        String city,
        String state,
        String supplierName
) {
    public AdressResponseDto(Adress adress) {
        this(
                adress.getId(),
                adress.getCep(),
                adress.getPublic_place(),
                adress.getNumber(),
                adress.getNeighborhood(),
                adress.getCity(),
                adress.getState(),
                adress.getSupplier() != null ? adress.getSupplier().getName() : null
        );
    }
}
