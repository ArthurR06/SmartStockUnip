package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Request.AdressRequestDto;
import com.br.api_controle_estoque.DTO.Response.AdressResponseDto;
import com.br.api_controle_estoque.model.Adress;
import com.br.api_controle_estoque.repository.AdressRepository;
import com.br.api_controle_estoque.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdressService {

    @Autowired
    private AdressRepository adressRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    public List<AdressResponseDto> listAdress() {
        return adressRepository.findAll()
                .stream()
                .map(AdressResponseDto::new)
                .toList();
    }

    public Optional<AdressResponseDto> findAdress(Long id) {
        return adressRepository.findById(id).map(AdressResponseDto::new);
    }

    public AdressResponseDto saveAdress(AdressRequestDto dto) {
        Adress adress = new Adress();
        adress.setCep(dto.cep());
        adress.setPublic_place(dto.public_place());
        adress.setNumber(dto.number());
        adress.setNeighborhood(dto.neighborhood());
        adress.setCity(dto.city());
        adress.setState(dto.state());

        adress = adressRepository.save(adress);

        return new AdressResponseDto(adress);
    }

    public AdressResponseDto updateAdress(Long id, AdressRequestDto dto) {
        Adress adress = adressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adress not found"));

        adress.setCep(dto.cep());
        adress.setPublic_place(dto.public_place());
        adress.setNumber(dto.number());
        adress.setNeighborhood(dto.neighborhood());
        adress.setCity(dto.city());
        adress.setState(dto.state());


        adress = adressRepository.save(adress);
        return new AdressResponseDto(adress);
    }

    public void deleteAdress(Long id) {
        adressRepository.deleteById(id);
    }
}

