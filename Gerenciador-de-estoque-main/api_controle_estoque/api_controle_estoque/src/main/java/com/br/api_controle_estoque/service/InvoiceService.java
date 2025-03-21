package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Request.InvoiceItemRequest;
import com.br.api_controle_estoque.DTO.Request.InvoiceRequestDto;
import com.br.api_controle_estoque.DTO.Response.InvoiceItemResponseDto;
import com.br.api_controle_estoque.DTO.Response.InvoiceResponseDto;
import com.br.api_controle_estoque.model.Invoice;
import com.br.api_controle_estoque.model.InvoiceItem;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.model.Supplier;
import com.br.api_controle_estoque.repository.InvoiceItemRepository;
import com.br.api_controle_estoque.repository.InvoiceRepository;
import com.br.api_controle_estoque.repository.ProductRepository;
import com.br.api_controle_estoque.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Invoice createInvoice(InvoiceRequestDto request) {
        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(request.invoiceNumber());
        invoice.setSupplier(supplier);
        invoice.setIssueDate(LocalDateTime.now());

        // Buscar os itens da fatura no banco usando os IDs fornecidos na requisição
        List<InvoiceItem> invoiceItems = request.invoiceItemIds().stream()
                .map(id -> invoiceItemRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Invoice Item not found with ID: " + id)))
                .collect(Collectors.toList());

        invoice.setItems(invoiceItems);

        // Agora que os itens já estão na fatura, podemos calcular o valor total corretamente
        invoice.updateTotalAmount();

        return invoiceRepository.save(invoice);
    }

    public List<InvoiceResponseDto> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoice -> new InvoiceResponseDto(
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
                                        item.getUnitPrice()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public InvoiceResponseDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        List<InvoiceItemResponseDto> items = invoice.getItems().stream()
                .map(item -> new InvoiceItemResponseDto(
                        item.getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice()))
                .collect(Collectors.toList());

        return new InvoiceResponseDto(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getSupplier().getName(),
                invoice.getIssueDate(),
                invoice.getTotalAmount(),
                items
        );
    }

    @Transactional
    public Invoice updateInvoice(Long id, InvoiceRequestDto dto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        invoice.setInvoiceNumber(dto.invoiceNumber());
        invoice.setIssueDate(LocalDateTime.now());
        invoice.setTotalAmount(invoice.calculateTotalAmount());

        return invoiceRepository.save(invoice);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));
        invoiceRepository.delete(invoice);
    }

}
