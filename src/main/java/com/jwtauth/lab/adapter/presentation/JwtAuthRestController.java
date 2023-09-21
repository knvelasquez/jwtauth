package com.jwtauth.lab.adapter.presentation;

import com.jwtauth.lab.application.EncryptService;
import com.jwtauth.lab.model.JwtRequestModel;
import com.jwtauth.lab.model.Ship;
import com.jwtlibrary.domain.JwtFactory;
import com.lab.authority.adapter.factory.AuthorityFactory;
import com.lab.authority.dto.AuthorityDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/jwt")
public class JwtAuthRestController {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthRestController.class);
    private final JwtFactory jwtFactory;
    private final EncryptService encryptService;

    public JwtAuthRestController(JwtFactory jwtFactory, EncryptService encryptService) {
        this.jwtFactory = jwtFactory;
        this.encryptService = encryptService;
    }

    @GetMapping(value = "/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("version", "v3.0");
        response.put("status", "healthy");
        response.put("details", "jwt/auth service is up and running.");
        response.put("date", new Date(System.currentTimeMillis()).toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/auth")
    public String getJwt(@Valid @RequestBody Ship ship) {
        final String issuer = "Jwt Auth Fintech, S.A";
        final String subject = "send encrypted private key the control center";
        final String company = "Space Ship Lab Company Dot Com";
        final String userName = String.valueOf(ship.getId());
        final String secret = "secret applied to private key";
        final String privateKey = encryptService.from(ship.getPrivateKey(), secret);
        final HashMap<String, Object> extraClaims = new HashMap<>();
        String jwt = jwtFactory.getEncoder().encode(issuer, subject, company, userName, privateKey, extraClaims);
        logger.info("jwt generated ..." + getLastCharacters(jwt));
        return jwt;
    }

    @RequestMapping(value = "/superhero.auth", method = RequestMethod.POST)
    public String createFrom(@Valid @RequestBody JwtRequestModel jwtModel) {
        final String issuer = "SuperHero Issuer, S.A";
        final String subject = "Subject Information";
        final String COMPANY = "SuperHero Fintech Company, S.A";
        final int idUser = jwtModel.getIdUser();
        final List<String> authorities = mapToAuthorityStringList(AuthorityFactory.getAuthority().findAll(idUser));
        return jwtFactory.getEncoder().encode(issuer, subject, COMPANY, String.valueOf(idUser), authorities);
    }

    private List<String> mapToAuthorityStringList(List<AuthorityDTO> authorities) {
        return authorities
                .stream()
                .map(authorityDto -> authorityDto.getAuthority().getDescription())
                .collect(Collectors.toList());
    }

    private String getLastCharacters(String jwt) {
        return jwt.substring(jwt.length() - 7);
    }
}
