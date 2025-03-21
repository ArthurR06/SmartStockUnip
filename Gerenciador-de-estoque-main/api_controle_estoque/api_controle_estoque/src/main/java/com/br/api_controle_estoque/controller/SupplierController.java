package com.br.api_controle_estoque.controller;
import com.br.api_controle_estoque.DTO.Request.SupplierRequestDto;
import com.br.api_controle_estoque.DTO.Response.AdressResponseDto;
import com.br.api_controle_estoque.DTO.Response.ProductResponseDto;
import com.br.api_controle_estoque.DTO.Response.SupplierResponseDto;
import com.br.api_controle_estoque.model.Adress;
import com.br.api_controle_estoque.model.Supplier;
import com.br.api_controle_estoque.service.AdressService;
import com.br.api_controle_estoque.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/supplier")
public class SupplierController {


    @Autowired
    private SupplierService supplierService;

    @Autowired
    private AdressService adressService;


    @Operation(summary = "Cadastrar um novo fornecedor", description = "Cria um novo fornecedor no sistema.")
    @ApiResponse(responseCode = "201", description = "fornecedor criado com sucesso")
    @PostMapping
    public ResponseEntity<SupplierResponseDto> createSupplier(@Valid @RequestBody SupplierRequestDto requestDto){

        SupplierResponseDto supplierResponse = supplierService.saveSupplier(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierResponse);
    }

    @Operation(summary = "Buscar todos os fornecedores", description = "Retorna uma lista com todos os fornecedores cadastrados.")
    @GetMapping("/list")
    public List<SupplierResponseDto> listSupplier(){
        return supplierService.listSupplier().stream()
                .map(SupplierResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar fornecedor por ID", description = "Retorna um fornecedor específico baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Fornecedor encontrado")
    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> searchSupplier (@PathVariable Long id){
        Supplier findSupplier = supplierService.searchSupplier(id);

        if( findSupplier == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SupplierResponseDto.fromEntity(findSupplier));
    }

    @Operation(summary = "Buscar fornecedor pelo nome", description = "Retorna uma lista ou um único fornecedor específico baseado no nome fornecido.")
    @ApiResponse(responseCode = "200", description = "Fornecedor encontrado")
    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    @GetMapping("/searchName")
    public ResponseEntity<List<SupplierResponseDto>> searchSupplierByName(@RequestParam(required = false) String name) {
        try {
            List<SupplierResponseDto> suppliers = supplierService.searchSupplierByName(name);
            return ResponseEntity.ok(suppliers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Atualizar fornecedor existente", description = "Atualiza as informações de um fornecedor existente.")
    @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> updateSupplier(@PathVariable Long id,
                                                   @Valid @RequestBody SupplierRequestDto requestDto){
        Supplier supplier = supplierService.searchSupplier(id);
        try {
            // Chama o service para atualizar o fornecedor
            SupplierResponseDto updatedSupplier = supplierService.updateSupplier(id, requestDto);
            return ResponseEntity.ok(updatedSupplier);
        } catch (RuntimeException e) {
            // Caso ocorra algum erro, retorna erro 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Deletar fornecedor", description = "Deleta um fornecedor do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Fornecedor deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id){
        Supplier existingSupplier = supplierService.searchSupplier(id);

        if(existingSupplier != null){
            supplierService.deleteSupplier(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }
}
