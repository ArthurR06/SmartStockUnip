package com.br.api_controle_estoque.DTO.Request;

import java.math.BigDecimal;

public record InvoiceItemRequest(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}
