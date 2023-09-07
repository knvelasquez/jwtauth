package model;

public class Ship {
    private final Long id;
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
