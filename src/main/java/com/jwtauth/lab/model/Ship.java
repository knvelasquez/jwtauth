package com.jwtauth.lab.model;

import jakarta.validation.constraints.*;

public class Ship {
    @NotNull(message = "must not be null")
    @Min(value = 1010, message = "must be greater or equal than 1010.")
    @Max(value = 1050, message = "must be lower or equal than 1050.")
    private final Long id;

    @Size(min = 5, max = 50, message = "must be between range 5 to 50 characters")
    @NotEmpty(message = "must not be empty")
    @NotNull(message = "must not be null")
    private final String privateKey;

    public Ship(Long id, String privateKey) {
        this.id = id;
        this.privateKey = privateKey;
    }

    public Long getId() {
        return id;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
