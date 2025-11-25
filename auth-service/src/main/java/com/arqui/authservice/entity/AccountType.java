package com.arqui.authservice.entity;

import lombok.Getter;

// Definimos los tipos de cuentas Premium o Basic
@Getter
public enum AccountType {
    BASIC("BASIC"),
    PREMIUM("PREMIUM");

    private final String type;

    AccountType(String type) {
        this.type = type;
    }
}
