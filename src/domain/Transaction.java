package domain;

import domain.enums.StatutTransaction;
import domain.enums.NiveauFrais;
import java.time.LocalDateTime;
import java.util.UUID;


public class Transaction {
    private final String id;
    private final String idPortefeuille;
    private final String adresseSource;
    private final String adresseDestination;
    private final double montant;
    private final double frais;
    private final NiveauFrais niveauFrais;
    private StatutTransaction statut;
    private final LocalDateTime dateCreation;
    
    public Transaction(String idPortefeuille, String adresseSource, String adresseDestination, 
                      double montant, double frais, NiveauFrais niveauFrais) {
        this.id = UUID.randomUUID().toString();
        this.idPortefeuille = idPortefeuille;
        this.adresseSource = adresseSource;
        this.adresseDestination = adresseDestination;
        this.montant = montant;
        this.frais = frais;
        this.niveauFrais = niveauFrais;
        this.statut = StatutTransaction.EN_ATTENTE;
        this.dateCreation = LocalDateTime.now();
    }
    
    public Transaction(String id, String idPortefeuille, String adresseSource, String adresseDestination,
                      double montant, double frais, NiveauFrais niveauFrais, 
                      StatutTransaction statut, LocalDateTime dateCreation) {
        this.id = id;
        this.idPortefeuille = idPortefeuille;
        this.adresseSource = adresseSource;
        this.adresseDestination = adresseDestination;
        this.montant = montant;
        this.frais = frais;
        this.niveauFrais = niveauFrais;
        this.statut = statut;
        this.dateCreation = dateCreation;
    }
    
    public String getId() { return id; }
    public String getIdPortefeuille() { return idPortefeuille; }
    public String getAdresseSource() { return adresseSource; }
    public String getAdresseDestination() { return adresseDestination; }
    public double getMontant() { return montant; }
    public double getFrais() { return frais; }
    public NiveauFrais getNiveauFrais() { return niveauFrais; }
    public StatutTransaction getStatut() { return statut; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    
    public void setStatut(StatutTransaction statut) { this.statut = statut; }
    
    @Override
    public String toString() {
        return String.format("Transaction [%s] %s -> %s | Montant: %.8f | Frais: %.4f$ | Statut: %s",
            id.substring(0, 8), adresseSource.substring(0, 8) + "...", 
            adresseDestination.substring(0, 8) + "...", montant, frais, statut);
    }
}