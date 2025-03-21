package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Request.LoginRequestDto;
import com.br.api_controle_estoque.DTO.Response.LoginResponseDto;
import com.br.api_controle_estoque.DTO.Request.RegisterRequestDto;
import com.br.api_controle_estoque.infra.security.TokenService;
import com.br.api_controle_estoque.model.User;
import com.br.api_controle_estoque.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Realizar um login de usuário", description = "Realiza o login de um usuário no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na autenticação do usuário. Pode ser causado por e-mail ou senha inválidos.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDto(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Cadastrar um novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar o usuário. Pode ocorrer se o e-mail já estiver em uso.")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new LoginResponseDto(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
