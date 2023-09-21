package com.jwtauth.lab.model;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class JwtsBuilder {
    @NotNull
    private final String issuer;
    @NotNull
    private final String subject;
    @NotNull
    private final String company;
    @NotNull
    private final String username;
    private final Map<String, Object> extraClaims;

    public JwtsBuilder(String issuer, String subject, String company, String username, Map<String, Object> extraClaims) {
        this.issuer = issuer;
        this.subject = subject;
        this.company = company;
        this.username = username;
        this.extraClaims = extraClaims;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getSubject() {
        return subject;
    }

    public String getCompany() {
        return company;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Object> getExtraClaims() {
        return extraClaims;
    }
}
