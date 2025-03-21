package com.br.api_controle_estoque.model.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    ATIVO, INATIVO;

    @JsonValue
    public String toLowerCase() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static Status fromString(String value) {
        return Status.valueOf(value.toUpperCase());
    }
}
