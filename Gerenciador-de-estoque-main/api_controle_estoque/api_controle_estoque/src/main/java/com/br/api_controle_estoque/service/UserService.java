package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Response.ProductResponseDto;
import com.br.api_controle_estoque.DTO.Response.UserResponseDto;
import com.br.api_controle_estoque.exceptions.NotFoundException;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.model.User;
import com.br.api_controle_estoque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listUsers(){

        return userRepository.findAll(Sort.by("name").ascending());
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<UserResponseDto> searchUsersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("O nome não pode ser vazio.");
        }

        List<User> users = userRepository.findByNameContainingIgnoreCase(name);

        if (users.isEmpty()) {
            throw new RuntimeException("Nenhum usuário encontrado.");
        }

        return users.stream()
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public User searchUser(Long id){
        return userRepository.findById(id).orElseThrow( () -> new NotFoundException("Nenhum usuario encontrado com esse id"));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
