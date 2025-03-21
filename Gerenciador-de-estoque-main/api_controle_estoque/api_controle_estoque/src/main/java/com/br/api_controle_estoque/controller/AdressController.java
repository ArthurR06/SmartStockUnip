package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Request.AdressRequestDto;
import com.br.api_controle_estoque.DTO.Response.AdressResponseDto;
import com.br.api_controle_estoque.model.Adress;
import com.br.api_controle_estoque.service.AdressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adress")
public class AdressController {

    @Autowired
    private AdressService adressService;


    @Operation(summary = "Cadastrar um novo endereço", description = "Cria um novo endereço no sistema.")
    @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso")
    @PostMapping
    public ResponseEntity<AdressResponseDto> createAdress(@RequestBody AdressRequestDto requestDto) {
        AdressResponseDto adress = adressService.saveAdress(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(adress);
    }

    @Operation(summary = "Buscar todos os endereços", description = "Retorna uma lista com todos os endereços cadastrados.")
    @GetMapping
    public List<AdressResponseDto> listAdress() {
        return adressService.listAdress();
    }

    @Operation(summary = "Buscar endereço por ID", description = "Retorna um endereço específico baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Endereço encontrado")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<AdressResponseDto> searchAdress(@PathVariable Long id) {
        Optional<AdressResponseDto> adress = adressService.findAdress(id);
        return adress.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar endereço existente", description = "Atualiza as informações de um enderço existente.")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<AdressResponseDto> updateAdress(@PathVariable Long id, @RequestBody AdressRequestDto dto) {
        AdressResponseDto updatedAdress = adressService.updateAdress(id, dto);
        return ResponseEntity.ok(updatedAdress);
    }

    @Operation(summary = "Deletar endereço", description = "Deleta um endereço do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Enderço deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdress(@PathVariable Long id) {
        if (adressService.findAdress(id).isPresent()) {
            adressService.deleteAdress(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
