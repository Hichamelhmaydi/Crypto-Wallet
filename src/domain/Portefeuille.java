package domain;

import domain.enums.TypeCrypto;
import java.time.LocalDateTime;
import java.util.UUID;


public class Portefeuille {
    private final String id;
    private final TypeCrypto typeCrypto;
    private final String adresse;
    private double solde;
    private final LocalDateTime dateCreation;
    
    public Portefeuille(TypeCrypto typeCrypto, String adresse) {
        this.id = UUID.randomUUID().toString();
        this.typeCrypto = typeCrypto;
        this.adresse = adresse;
        this.solde = 0.0;
        this.dateCreation = LocalDateTime.now();
    }
    
    public Portefeuille(String id, TypeCrypto typeCrypto, String adresse, double solde, LocalDateTime dateCreation) {
        this.id = id;
        this.typeCrypto = typeCrypto;
        this.adresse = adresse;
        this.solde = solde;
        this.dateCreation = dateCreation;
    }
    
    // Getters
    public String getId() { return id; }
    public TypeCrypto getTypeCrypto() { return typeCrypto; }
    public String getAdresse() { return adresse; }
    public double getSolde() { return solde; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    
    public void setSolde(double solde) { this.solde = solde; }
    
    @Override
    public String toString() {
        return String.format("Portefeuille [%s] %s - Solde: %.8f %s", 
            id.substring(0, 8), adresse, solde, typeCrypto);
    }
}