package config;

/**
 * Classe contenant toutes les requêtes SQL utilisées dans l'application
 * Centralise les requêtes pour faciliter la maintenance
 */
public class SQL {
    
    // Requêtes pour la table des portefeuilles
    public static final String CREER_TABLE_PORTEFEUILLES = 
        "CREATE TABLE IF NOT EXISTS portefeuilles (" +
        "id VARCHAR(36) PRIMARY KEY, " +
        "type_crypto VARCHAR(20) NOT NULL, " +
        "adresse VARCHAR(50) UNIQUE NOT NULL, " +
        "solde DECIMAL(15,8) DEFAULT 0.0, " +
        "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    
    public static final String INSERER_PORTEFEUILLE = 
        "INSERT INTO portefeuilles (id, type_crypto, adresse, solde) VALUES (?, ?, ?, ?)";
    
    public static final String SELECTIONNER_PORTEFEUILLE_PAR_ID = 
        "SELECT * FROM portefeuilles WHERE id = ?";
    
    public static final String SELECTIONNER_TOUS_PORTEFEUILLES = 
        "SELECT * FROM portefeuilles";
    
    // Requêtes pour la table des transactions
    public static final String CREER_TABLE_TRANSACTIONS = 
        "CREATE TABLE IF NOT EXISTS transactions (" +
        "id VARCHAR(36) PRIMARY KEY, " +
        "id_portefeuille VARCHAR(36) NOT NULL, " +
        "adresse_source VARCHAR(50) NOT NULL, " +
        "adresse_destination VARCHAR(50) NOT NULL, " +
        "montant DECIMAL(15,8) NOT NULL, " +
        "frais DECIMAL(10,4) NOT NULL, " +
        "niveau_frais VARCHAR(20) NOT NULL, " +
        "statut VARCHAR(20) NOT NULL, " +
        "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
        "FOREIGN KEY (id_portefeuille) REFERENCES portefeuilles(id))";
    
    public static final String INSERER_TRANSACTION = 
        "INSERT INTO transactions (id, id_portefeuille, adresse_source, adresse_destination, " +
        "montant, frais, niveau_frais, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    public static final String SELECTIONNER_TRANSACTIONS_PAR_PORTEFEUILLE = 
        "SELECT * FROM transactions WHERE id_portefeuille = ?";
    
    public static final String SELECTIONNER_TOUTES_TRANSACTIONS = 
        "SELECT * FROM transactions ORDER BY frais DESC";
    
    public static final String METTRE_A_JOUR_STATUT_TRANSACTION = 
        "UPDATE transactions SET statut = ? WHERE id = ?";
}