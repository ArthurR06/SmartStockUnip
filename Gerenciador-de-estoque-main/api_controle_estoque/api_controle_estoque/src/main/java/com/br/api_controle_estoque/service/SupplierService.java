package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Request.SupplierRequestDto;
import com.br.api_controle_estoque.DTO.Response.AdressResponseDto;
import com.br.api_controle_estoque.DTO.Response.ProductResponseDto;
import com.br.api_controle_estoque.DTO.Response.SupplierResponseDto;
import com.br.api_controle_estoque.exceptions.NotFoundException;
import com.br.api_controle_estoque.model.Adress;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.model.Supplier;
import com.br.api_controle_estoque.repository.AdressRepository;
import com.br.api_controle_estoque.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AdressService adressService;

    @Autowired
    private AdressRepository adressRepository;

    public SupplierResponseDto saveSupplier(SupplierRequestDto requestDto) {
        // Buscar o AdressResponseDto para exibição
        Optional<AdressResponseDto> adressDto = adressService.findAdress(requestDto.adressId());

        if (adressDto.isEmpty()) {
            throw new RuntimeException("Endereço não encontrado!");
        }

        // Buscar a entidade Adress para salvar no Supplier
        Adress adress = adressRepository.findById(requestDto.adressId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));

        // Criar o Supplier e associar o endereço
        Supplier supplier = new Supplier();
        supplier.setName(requestDto.name());
        supplier.setPhone(requestDto.phone());
        supplier.setEmail(requestDto.email());
        supplier.setCnpj(requestDto.cnpj());
        supplier.setAdress(adress);

        // Salvar no banco
        supplier = supplierRepository.save(supplier);

        // Após salvar o fornecedor, atualize o campo id_fornecedor no endereço
        if (supplier.getAdress() != null) {
            Adress adressNew = supplier.getAdress();
            adress.setSupplier(supplier);
            adressRepository.save(adressNew);
            }

        // Retornar DTO do Supplier com apenas o id do endereço
        return new SupplierResponseDto(supplier);
    }

    public SupplierResponseDto updateSupplier(Long id, SupplierRequestDto requestDto) {
        // Buscar o AdressResponseDto para exibição
        Optional<AdressResponseDto> adressDto = adressService.findAdress(requestDto.adressId());
        if (adressDto.isEmpty()) {
            throw new RuntimeException("Endereço não encontrado!");
        }

        // Buscar a entidade Adress para salvar no Supplier
        Adress adress = adressRepository.findById(requestDto.adressId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));

        // Buscar o fornecedor existente
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado!"));

        // Atualizar dados do fornecedor
        supplier.setName(requestDto.name());
        supplier.setPhone(requestDto.phone());
        supplier.setEmail(requestDto.email());
        supplier.setCnpj(requestDto.cnpj());
        supplier.setAdress(adress);

        // Salvar o fornecedor
        supplier = supplierRepository.save(supplier);

        // Após salvar o fornecedor, atualizar o campo id_fornecedor no endereço
        if (supplier.getAdress() != null) {
            Adress adressNew = supplier.getAdress();
            adress.setSupplier(supplier);
            adressRepository.save(adressNew);
        }

        // Retornar o DTO com apenas o id do endereço
        return new SupplierResponseDto(supplier);
    }

    public List<SupplierResponseDto> searchSupplierByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("O nome não pode ser vazio.");
        }

        List<Supplier> suppliers = supplierRepository.findByNameContainingIgnoreCase(name);

        // Caso não encontre produtos, lança uma exceção ou retorna uma lista vazia
        if (suppliers.isEmpty()) {
            throw new RuntimeException("Nenhum fornecedor encontrado.");
        }

        // Converte a lista de produtos para DTOs e retorna
        return suppliers.stream()
                .map(SupplierResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<Supplier> listSupplier(){
        return supplierRepository.findAll();
    }

    public Supplier searchSupplier(Long id){
        return supplierRepository.findById(id).orElseThrow( () -> new NotFoundException("Nenhum fornecedor encontrado com esse id."));
    }

    public void deleteSupplier(Long id){
        supplierRepository.deleteById(id);
    }
}
