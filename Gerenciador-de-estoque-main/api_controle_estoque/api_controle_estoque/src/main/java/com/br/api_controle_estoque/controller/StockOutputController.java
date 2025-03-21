package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Request.StockOutputRequestDto;
import com.br.api_controle_estoque.DTO.Response.StockOutputResponseDto;
import com.br.api_controle_estoque.model.StockOutput;
import com.br.api_controle_estoque.service.StockOutputService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock-output")
public class StockOutputController {

    @Autowired
    private StockOutputService stockOutputService;
    @Operation(summary = "Cadastrar uma nova movimentação de saída do estoque.",
            description = "Cria uma nova movimentação de saída no estoque no sistema. Esta ação também atualizará a tabela de produtos, diminuindo o estoque.")
    @ApiResponse(responseCode = "201", description = "Movimentação de saída criada com sucesso. O estoque do produto será atualizado automaticamente.")
    @PostMapping
    public ResponseEntity<StockOutputResponseDto> createStockOutput(
            @RequestBody @Valid StockOutputRequestDto dto) {

        StockOutput stockOutput = stockOutputService.createStockOutput(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StockOutputResponseDto(
                stockOutput.getId(),
                stockOutput.getProduct().getName(),
                stockOutput.getProduct().getId(),
                stockOutput.getQuantity(),
                stockOutput.getOutputDate(),
                stockOutput.getObservation()
        ));
    }

    @Operation(summary = "Buscar todas movimentações de saída criadas", description = "Retorna uma lista com todas movimentações de saída cadastradas.")
    @GetMapping
    public ResponseEntity<List<StockOutputResponseDto>> getAllStockOutputs() {
        return ResponseEntity.ok(stockOutputService.getAllStockOutputs());
    }

    @Operation(summary = "Buscar uma movimentação de saída por ID", description = "Retorna um item de movimentação de saída baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Movimentação encontrada")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<StockOutputResponseDto> getStockOutputById(@PathVariable Long id) {
        return ResponseEntity.ok(stockOutputService.getStockOutputById(id));
    }

    @Operation(summary = "Atualizar uma movimentação de saída existente", description = "Atualiza as informações de uma movimentação de saída existente. " +
            "Esta ação também atualizará a tabela de produtos, alterando o estoque.")
    @ApiResponse(responseCode = "200", description = "Movimenetação atualizada com sucesso. ")
    @ApiResponse(responseCode = "404", description = "Movimentação encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<StockOutputResponseDto> updateStockOutput(@RequestBody @Valid StockOutputRequestDto dto) {
        StockOutput updatedStockOutput = stockOutputService.createStockOutput(dto);
        return ResponseEntity.ok(new StockOutputResponseDto(
                updatedStockOutput.getId(),
                updatedStockOutput.getProduct().getName(),
                updatedStockOutput.getProduct().getId(),
                updatedStockOutput.getQuantity(),
                updatedStockOutput.getOutputDate(),
                updatedStockOutput.getObservation()
        ));
    }

    @Operation(summary = "Deletar uma movimentação de saída",
            description = "Deleta uma movimentação de saída do sistema com base no ID fornecido. " +
                    "Esta ação também atualizará a tabela de produtos, alterando o estoque.")
    @ApiResponse(responseCode = "204", description = "Movimentação deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockOutput(@PathVariable Long id) {
        stockOutputService.deleteStockOutput(id);
        return ResponseEntity.noContent().build();
    }
}
