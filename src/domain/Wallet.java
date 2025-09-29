package domain;

import domain.enums.CryptoType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Wallet {
    private final String id;
    private final String adresse;
    private final CryptoType typeCrypto;
    private BigDecimal solde;
    private final LocalDateTime dateCreation;

    public Wallet(String adresse, CryptoType typeCrypto) {
        this.id = UUID.randomUUID().toString();
        this.adresse = adresse;
        this.typeCrypto = typeCrypto;
        this.solde = BigDecimal.ZERO;
        this.dateCreation = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getAdresse() { return adresse; }
    public CryptoType getTypeCrypto() { return typeCrypto; }
    public BigDecimal getSolde() { return solde; }
    public LocalDateTime getDateCreation() { return dateCreation; }

    public void setSolde(BigDecimal solde) { this.solde = solde; }

    @Override
    public String toString() {
        return String.format("Wallet[%s] - %s - Solde: %s %s", 
            adresse.substring(0, 8) + "...", 
            typeCrypto, 
            solde, 
            typeCrypto);
    }
}