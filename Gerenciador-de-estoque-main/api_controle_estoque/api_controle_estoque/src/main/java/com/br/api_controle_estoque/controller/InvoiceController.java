package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Request.InvoiceRequestDto;
import com.br.api_controle_estoque.DTO.Response.InvoiceResponseDto;
import com.br.api_controle_estoque.model.Invoice;
import com.br.api_controle_estoque.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Operation(summary = "Cadastrar uma nova nota fiscal",
            description = "Cria uma nova nota fiscal no sistema.")
    @ApiResponse(responseCode = "201", description = "Nota fiscal criada com sucesso.")
    @PostMapping
    public ResponseEntity<InvoiceResponseDto> createInvoice(@RequestBody @Valid InvoiceRequestDto request) {
        Invoice invoice = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new InvoiceResponseDto(invoice));
    }

    @Operation(summary = "Buscar todas as notas fiscais criado", description = "Retorna uma lista com todas notas fiscais cadastradas.")
    @GetMapping
    public ResponseEntity<List<InvoiceResponseDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @Operation(summary = "Buscar uma nota fiscal por ID", description = "Retorna uma nota fiscal específica baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Nota fiscal encontrada")
    @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }


    @Operation(summary = "Atualizar uma nota fiscal existente", description = "Atualiza as informações de uma nota fiscal existente.")
    @ApiResponse(responseCode = "200", description = "Nota fiscal atualizada com sucesso. ")
    @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> updateInvoice(@PathVariable Long id, @RequestBody @Valid InvoiceRequestDto dto) {
        Invoice invoice = invoiceService.updateInvoice(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new InvoiceResponseDto(invoice));
    }

    @Operation(summary = "Deletar uma nota fiscal",
            description = "Deleta uma nota fiscal do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Nota fiscal deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
