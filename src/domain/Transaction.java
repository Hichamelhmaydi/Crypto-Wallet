package domain;

import domain.enums.CryptoType;
import domain.enums.FeeLevel;
import domain.enums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final String id;
    private final String adresseSource;
    private final String adresseDestination;
    private final BigDecimal montant;
    private final BigDecimal frais;
    private final FeeLevel niveauFrais;
    private TransactionStatus statut;
    private final LocalDateTime dateCreation;
    private final CryptoType typeCrypto;

    public Transaction(String adresseSource, String adresseDestination, 
            BigDecimal montant, BigDecimal frais, FeeLevel niveauFrais, CryptoType typeCrypto) {
        this.id = UUID.randomUUID().toString();
        this.adresseSource = adresseSource;
        this.adresseDestination = adresseDestination;
        this.montant = montant;
        this.frais = frais;
        this.niveauFrais = niveauFrais;
        this.statut = TransactionStatus.EN_ATTENTE;
        this.dateCreation = LocalDateTime.now();
        this.typeCrypto = typeCrypto;
    }


    public String getId() { return id; }
    public String getAdresseSource() { return adresseSource; }
    public String getAdresseDestination() { return adresseDestination; }
    public BigDecimal getMontant() { return montant; }
    public BigDecimal getFrais() { return frais; }
    public FeeLevel getNiveauFrais() { return niveauFrais; }
    public TransactionStatus getStatut() { return statut; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public CryptoType getTypeCrypto() { return typeCrypto; }

    public void setStatut(TransactionStatus statut) { this.statut = statut; }

    @Override
    public String toString() {
        return String.format("Transaction[%s] %s → %s | Montant: %s | Frais: %s | Statut: %s",
            id.substring(0, 8) + "...",
            adresseSource.substring(0, 8) + "...",
            adresseDestination.substring(0, 8) + "...",
            montant, frais, statut);
    }
}