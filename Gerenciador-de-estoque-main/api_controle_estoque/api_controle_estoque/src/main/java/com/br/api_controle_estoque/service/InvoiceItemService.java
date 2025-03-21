package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Request.InvoiceItemRequest;
import com.br.api_controle_estoque.DTO.Response.InvoiceItemResponseDto;
import com.br.api_controle_estoque.model.InvoiceItem;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.repository.InvoiceItemRepository;
import com.br.api_controle_estoque.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;
    @Autowired
    private ProductRepository productRepository;


    // Método para excluir o que a movimentação tinha retirado ou colocado no estoque
    private void revertStockMovement(Product product, InvoiceItem invoiceItem){
            product.setQuantity_stock(product.getQuantity_stock() - invoiceItem.getQuantity());
    }

    //Método para atualizar a tabela de produtos
    private void updateProductStock(Product product, InvoiceItem invoiceItem) {
            product.setQuantity_stock(product.getQuantity_stock() + invoiceItem.getQuantity());
    }

    public InvoiceItem createInvoiceItem(InvoiceItemRequest dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + dto.productId()));

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setProduct(product);
        invoiceItem.setQuantity(dto.quantity());
        invoiceItem.setUnitPrice(dto.unitPrice());

        updateProductStock(product, invoiceItem);
        productRepository.save(product);

        return invoiceItemRepository.save(invoiceItem);
    }

    public List<InvoiceItemResponseDto> getAllInvoiceItems() {
        return invoiceItemRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public InvoiceItemResponseDto getInvoiceItemById(Long id) {
        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice Item not found with ID: " + id));
        return convertToDto(invoiceItem);
    }

    public InvoiceItem updateInvoiceItem(Long id, InvoiceItemRequest dto) {
        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice Item not found with ID: " + id));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + dto.productId()));


        revertStockMovement(product, invoiceItem);
        productRepository.save(product);

        invoiceItem.setProduct(product);
        invoiceItem.setQuantity(dto.quantity());
        invoiceItem.setUnitPrice(dto.unitPrice());

        updateProductStock(product, invoiceItem);
        productRepository.save(product);
        return invoiceItemRepository.save(invoiceItem);
    }

    public void deleteInvoiceItem(Long id) {
        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice item not found"));

        Product product = invoiceItem.getProduct();
        revertStockMovement(product, invoiceItem);
        productRepository.save(product);
        invoiceItemRepository.deleteById(id);
    }

    private InvoiceItemResponseDto convertToDto(InvoiceItem invoiceItem) {
        return new InvoiceItemResponseDto(
                invoiceItem.getId(),
                invoiceItem.getProduct().getName(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice()
        );
    }

}
