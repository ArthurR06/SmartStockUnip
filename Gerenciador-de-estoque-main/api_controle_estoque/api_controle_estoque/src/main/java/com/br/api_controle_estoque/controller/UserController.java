package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Response.ProductResponseDto;
import com.br.api_controle_estoque.DTO.Response.UserResponseDto;
import com.br.api_controle_estoque.model.User;
import com.br.api_controle_estoque.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Buscar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados.")
    @GetMapping("/list")
    public List<UserResponseDto> listUsers(){
        return userService.listUsers().stream()
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> searchUser(@PathVariable Long id){
        User findUser = userService.searchUser(id);

        if (findUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserResponseDto.fromEntity(findUser));
    }

    @Operation(summary = "Buscar usuário pelo nome", description = "Retorna uma lista ou um único usuário específico baseado no nome fornecido.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping("/searchName")
    public ResponseEntity<List<UserResponseDto>> searchUserByName(@RequestParam(required = false) String name) {
        try {
            List<UserResponseDto> users = userService.searchUsersByName(name);
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Atualizar usuário existente", description = "Atualiza as informações de um usuário existente.")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @Valid @RequestBody User user){
        User existingUser = userService.searchUser(id);

        if (existingUser != null){
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return ResponseEntity.ok(userService.saveUser(existingUser));
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Deletar usuário ", description = "Deleta um usuário  do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        User existingUser = userService.searchUser(id);
        if (existingUser != null){
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
