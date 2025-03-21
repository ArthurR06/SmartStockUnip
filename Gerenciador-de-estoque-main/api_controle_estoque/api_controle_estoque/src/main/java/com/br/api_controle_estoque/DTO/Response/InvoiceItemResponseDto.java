package com.br.api_controle_estoque.DTO.Response;

import java.math.BigDecimal;

public record InvoiceItemResponseDto(
        Long id,
        String productName,
        Integer quantity,
        BigDecimal unitPrice
) {
}
