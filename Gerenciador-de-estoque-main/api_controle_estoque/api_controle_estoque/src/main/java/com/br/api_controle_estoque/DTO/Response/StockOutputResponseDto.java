package com.br.api_controle_estoque.DTO.Response;

import java.time.LocalDateTime;

public record StockOutputResponseDto(
        Long id,
        String productName,
        Long idProduct,
        Integer quantity,
        LocalDateTime outputDate,
        String observation
) {
}
