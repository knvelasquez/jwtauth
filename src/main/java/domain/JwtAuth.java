package domain;

import java.util.List;

public interface JwtAuth {
    String from(Long shipId, String privateKey);

    String from(Long shipId, String privateKey, List<String> extraClaims);
}
