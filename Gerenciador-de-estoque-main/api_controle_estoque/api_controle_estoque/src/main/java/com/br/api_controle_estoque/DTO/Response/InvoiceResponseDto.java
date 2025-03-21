package com.br.api_controle_estoque.DTO.Response;

import com.br.api_controle_estoque.model.Invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record InvoiceResponseDto(
        Long id,
        String invoiceNumber,
        String supplierName,
        LocalDateTime issueDate,
        BigDecimal totalAmount,
        List<InvoiceItemResponseDto> items
) {
    public InvoiceResponseDto(Invoice invoice) {
        this(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getSupplier().getName(),
                invoice.getIssueDate(),
                invoice.getTotalAmount(),
                invoice.getItems().stream()
                        .map(item -> new InvoiceItemResponseDto(
                                item.getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getUnitPrice()
                        ))
                        .collect(Collectors.toList())
        );
    }
}

