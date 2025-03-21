package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Response.ProductResponseDto;
import com.br.api_controle_estoque.exceptions.NotFoundException;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;



    public List<Product> listProduct(){
        return productRepository.findAll(Sort.by("name").ascending());
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<ProductResponseDto> searchProductByName(String name) {
        // Verifica se o nome é válido
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("O nome não pode ser vazio.");
        }

        // Busca produtos cujos nomes contenham a string "name"
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);

        // Caso não encontre produtos, lança uma exceção ou retorna uma lista vazia
        if (products.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado.");
        }

        // Converte a lista de produtos para DTOs e retorna
        return products.stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Product searchProduct(Long id){
        return productRepository.findById(id).orElseThrow( () -> new NotFoundException("Nenhum produto encontrado com esse id"));
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
