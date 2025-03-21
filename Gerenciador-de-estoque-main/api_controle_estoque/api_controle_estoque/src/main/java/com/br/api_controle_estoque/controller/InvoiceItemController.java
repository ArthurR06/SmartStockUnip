package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Request.InvoiceItemRequest;
import com.br.api_controle_estoque.DTO.Response.InvoiceItemResponseDto;
import com.br.api_controle_estoque.model.InvoiceItem;
import com.br.api_controle_estoque.service.InvoiceItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice-items")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService invoiceItemService;

    @Operation(summary = "Cadastrar um novo item para a nota fiscal",
            description = "Cria um novo item para a nota fiscal no sistema. Esta ação também atualizará a tabela de produtos, aumentando o estoque.")
    @ApiResponse(responseCode = "201", description = "Item nota fiscal criado com sucesso. O estoque do produto será atualizado automaticamente.")
    @PostMapping
    public ResponseEntity<InvoiceItemResponseDto> createInvoiceItem(
            @RequestBody @Valid InvoiceItemRequest dto) {
        InvoiceItem invoiceItem = invoiceItemService.createInvoiceItem(dto);
        return ResponseEntity.status(HttpStatus
                        .CREATED)
                .body(new InvoiceItemResponseDto(
                        invoiceItem.getId(),
                        invoiceItem.getProduct().getName(),
                        invoiceItem.getQuantity(),
                        invoiceItem.getUnitPrice()
                ));
    }

    @Operation(summary = "Buscar todos os itens de nota fiscal criado", description = "Retorna uma lista com todos os itens de nota fiscal cadastrados.")
    @GetMapping
    public ResponseEntity<List<InvoiceItemResponseDto>> getAllInvoiceItems() {
        return ResponseEntity.ok(invoiceItemService.getAllInvoiceItems());
    }

    @Operation(summary = "Buscar um item de nota fiscal por ID", description = "Retorna um item de nota fiscal específico baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Item de nota fiscal encontrado")
    @ApiResponse(responseCode = "404", description = "Item de nota fiscal não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceItemResponseDto> getInvoiceItemById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceItemService.getInvoiceItemById(id));
    }

    @Operation(summary = "Atualizar um item de nota fiscal existente", description = "Atualiza as informações de um item de nota fiscal existente. Esta ação também atualizará a tabela de produtos, alterando o estoque.")
    @ApiResponse(responseCode = "200", description = "Item de nota fiscal atualizado atualizado com sucesso. ")
    @ApiResponse(responseCode = "404", description = "Item de nota fiscal não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceItemResponseDto> updateInvoiceItem(@PathVariable Long id, @RequestBody @Valid InvoiceItemRequest dto) {
        InvoiceItem updatedItem = invoiceItemService.updateInvoiceItem(id, dto);
        return ResponseEntity.ok(new InvoiceItemResponseDto(
                updatedItem.getId(),
                updatedItem.getProduct().getName(),
                updatedItem.getQuantity(),
                updatedItem.getUnitPrice()
        ));
    }

    @Operation(summary = "Deletar um item de nota fiscal",
            description = "Deleta um item de nota fiscal do sistema com base no ID fornecido. Esta ação também atualizará a tabela de produtos, alterando o estoque.")
    @ApiResponse(responseCode = "204", description = "Item de nota fiscal deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Item de nota fiscal não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceItem(@PathVariable Long id) {
        invoiceItemService.deleteInvoiceItem(id);
        return ResponseEntity.noContent().build();
    }
}
