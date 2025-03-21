package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Request.StockOutputRequestDto;
import com.br.api_controle_estoque.DTO.Response.StockOutputResponseDto;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.model.StockOutput;
import com.br.api_controle_estoque.repository.ProductRepository;
import com.br.api_controle_estoque.repository.StockOutputRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockOutputService {

    @Autowired
    private StockOutputRepository stockOutputRepository;
    @Autowired
    private ProductRepository productRepository;


    // Método para excluir o que a movimentação tinha retirado ou colocado no estoque
    private void revertStockMovement(Product product, StockOutput stockOutput){
            product.setQuantity_stock(product.getQuantity_stock() + stockOutput.getQuantity());
    }

    //Método para atualizar a tabela de produtos
    private void updateProductStock(Product product, StockOutput stockOutput) {
            if (product.getQuantity_stock() < stockOutput.getQuantity()) {
                throw new RuntimeException("Quantidade insuficiente em estoque");
            } else {
                product.setQuantity_stock(product.getQuantity_stock() - stockOutput.getQuantity());
            }
        }


    @Transactional
    public StockOutput createStockOutput(StockOutputRequestDto dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if (product.getQuantity_stock() < dto.quantity()) {
            throw new RuntimeException("Quantidade insuficiente em estoque para essa saída");
        }

        StockOutput stockOutput = new StockOutput();
        stockOutput.setProduct(product);
        stockOutput.setQuantity(dto.quantity());
        stockOutput.setOutputDate(LocalDateTime.now());
        stockOutput.setObservation(dto.observation());

        updateProductStock(product, stockOutput);
        productRepository.save(product);
        return stockOutputRepository.save(stockOutput);
    }

    public List<StockOutputResponseDto> getAllStockOutputs() {
        return stockOutputRepository.findAll().stream()
                .map(stockOutput -> new StockOutputResponseDto(
                        stockOutput.getId(),
                        stockOutput.getProduct().getName(),
                        stockOutput.getProduct().getId(),
                        stockOutput.getQuantity(),
                        stockOutput.getOutputDate(),
                        stockOutput.getObservation()
                ))
                .collect(Collectors.toList());
    }

    public StockOutputResponseDto getStockOutputById(Long id) {
        StockOutput stockOutput = stockOutputRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock Output not found"));
        return new StockOutputResponseDto(
                stockOutput.getId(),
                stockOutput.getProduct().getName(),
                stockOutput.getProduct().getId(),
                stockOutput.getQuantity(),
                stockOutput.getOutputDate(),
                stockOutput.getObservation()
        );
    }

    @Transactional
    public StockOutput updateStockOutput(StockOutputRequestDto dto) {
        StockOutput stockOutput = stockOutputRepository.findById(dto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Stock Output not found"));

        Product product = stockOutput.getProduct();
        revertStockMovement(product, stockOutput);
        productRepository.save(product);

        stockOutput.setQuantity(dto.quantity());
        stockOutput.setObservation(dto.observation());

        updateProductStock(product, stockOutput);
        productRepository.save(product);
        return stockOutputRepository.save(stockOutput);
    }

    @Transactional
    public void deleteStockOutput(Long id) {
        StockOutput stockOutput = stockOutputRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock Output not found"));

        Product product = stockOutput.getProduct();
        revertStockMovement(product, stockOutput);
        productRepository.save(product);

        stockOutputRepository.delete(stockOutput);
    }

}
