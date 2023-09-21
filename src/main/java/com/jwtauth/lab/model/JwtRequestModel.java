package com.jwtauth.lab.model;

import jakarta.validation.constraints.Min;

public class JwtRequestModel {
    @Min(value = 1, message = "must be greater than zero.")
    private int idUser;

    public JwtRequestModel() {
    }

    public JwtRequestModel(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "{" +
                "\"idUser\":" + idUser +
                '}';
    }
}
