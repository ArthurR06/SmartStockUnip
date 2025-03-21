package com.br.api_controle_estoque.DTO.Request;

import java.math.BigDecimal;
import java.util.List;

public record InvoiceRequestDto(
        String invoiceNumber,
        Long supplierId,
        List<Long> invoiceItemIds
) {
}
