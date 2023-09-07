package adapter.presentation;

import domain.JwtAuth;
import model.Ship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/jwt")
public class JwtAuthRestController {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthRestController.class);
    private final JwtAuth jwtAuth;

    public JwtAuthRestController(JwtAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    @GetMapping(value = "/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("version", "v2.0");
        response.put("status", "healthy");
        response.put("details", "jwt/auth service is up and running.");
        response.put("date", new Date(System.currentTimeMillis()).toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/auth")
    public String getJwt(@RequestBody Ship ship) {
        String jwt = jwtAuth.from(ship.getId(), ship.getPrivateKey());
        logger.info("jwt generated ..." + getLastCharacters(jwt));
        return jwt;
    }

    private String getLastCharacters(String jwt) {
        return jwt.substring(jwt.length() - 7);
    }
}
