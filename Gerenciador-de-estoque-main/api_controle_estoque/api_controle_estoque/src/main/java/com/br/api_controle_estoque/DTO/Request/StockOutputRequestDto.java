package com.br.api_controle_estoque.DTO.Request;

public record StockOutputRequestDto(
        Long productId,
        Integer quantity,
        String observation
) {
}
