package com.br.api_controle_estoque.DTO.Response;
import com.br.api_controle_estoque.model.Supplier;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierResponseDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String cnpj;
    private String cep;
    private String public_place;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private List<String> productNames;

    public SupplierResponseDto(Long id, String name, String phone, String email,
                               String cnpj, String cep, String public_place,
                               String number, String neighborhood, String city,
                               String state, List<String> productNames) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.cnpj = cnpj;
        this.cep = cep;
        this.public_place = public_place;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.productNames = productNames;
    }

    public SupplierResponseDto(Supplier supplier) {
        this.id = supplier.getId();
        this.name = supplier.getName();
        this.phone = supplier.getPhone();
        this.email = supplier.getEmail();
        this.cnpj = supplier.getCnpj();
        if (supplier.getAdress() != null) {
            this.cep = supplier.getAdress().getCep();
            this.public_place = supplier.getAdress().getPublic_place();
            this.number = supplier.getAdress().getNumber();
            this.neighborhood = supplier.getAdress().getNeighborhood();
            this.city = supplier.getAdress().getCity();
            this.state = supplier.getAdress().getState();
        }
        this.productNames = supplier.getInvoices() != null ?
                supplier.getInvoices().stream()
                        .flatMap(invoice -> invoice.getItems().stream()) // Pega os itens das notas fiscais
                        .map(item -> item.getProduct().getName()) // Obtém o nome do produto
                        .distinct() // Evita duplicatas
                        .collect(Collectors.toList())
                : null;
    }

    public static SupplierResponseDto fromEntity(Supplier supplier) {
        return new SupplierResponseDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getCnpj(),
                supplier.getAdress() != null ? supplier.getAdress().getCep() : null,
                supplier.getAdress() != null ? supplier.getAdress().getPublic_place() : null,
                supplier.getAdress() != null ? supplier.getAdress().getNumber() : null,
                supplier.getAdress() != null ? supplier.getAdress().getNeighborhood() : null,
                supplier.getAdress() != null ? supplier.getAdress().getCity() : null,
                supplier.getAdress() != null ? supplier.getAdress().getState() : null,
                supplier.getInvoices() != null ?
                        supplier.getInvoices().stream()
                                .flatMap(invoice -> invoice.getItems().stream())
                                .map(item -> item.getProduct().getName())
                                .distinct()
                                .collect(Collectors.toList())
                        : null
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPublic_place() {
        return public_place;
    }

    public void setPublic_place(String public_place) {
        this.public_place = public_place;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
