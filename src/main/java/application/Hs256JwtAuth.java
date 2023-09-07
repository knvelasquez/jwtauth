package application;

import domain.JwtAuth;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;


@Service
public class Hs256JwtAuth implements JwtAuth {
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final String AUTHORITIES = "authorities";
    private static final String ISSUER = "Jwt Auth Fintech, S.A";
    private static final String SUBJECT = "send encrypted private key the control center";
    private static final String COMPANY = "Lab Company Dot Com";
    private static final String PRIVATE_KEY_CLAIM = "PRIVATE_KEY";
    private static final String SHIP_ID_CLAIM = "SHIP_ID";
    private static final SignatureAlgorithm SIGNATURE = SignatureAlgorithm.HS256;//SignatureAlgorithm.HS512;

    private final EncryptService encryptService;

    public Hs256JwtAuth(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @Override
    public String from(Long shipId, String privateKey) {
        return create(shipId, privateKey, new ArrayList<>());
    }

    @Override
    public String from(Long shipId, String privateKey, List<String> extraClaims) {
        return create(shipId, privateKey, extraClaims);
    }

    private String create(Long shipId, String privateKey, List<String> extraClaimsList) {
        Map<String, Object> extraClaimsMap = new HashMap<>();
        if (!extraClaimsList.isEmpty()) {
            extraClaimsMap.put(AUTHORITIES, extraClaimsList);
        }
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(SUBJECT)
                .setClaims(extraClaimsMap)
                .claim(COMPANY, ISSUER)
                .claim(PRIVATE_KEY_CLAIM, encryptService.from(privateKey, "")) //Set private key as a custom claim
                .claim(SHIP_ID_CLAIM, shipId)//Set ship id as a custom claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //set token for 24 min
                .signWith(getSignInKey(), SIGNATURE)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
