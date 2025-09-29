package config;

import java.sql.Connection;
import java.sql.Statement;

public class SQL {
    private DatabaseConfig configBaseDeDonnees;

    public SQL() {
        this.configBaseDeDonnees = DatabaseConfig.getInstance();
    }

    public void creerTables() {
        String sqlWallet = "CREATE TABLE IF NOT EXISTS wallets (" +
                "id VARCHAR(36) PRIMARY KEY," +
                "adresse VARCHAR(50) UNIQUE NOT NULL," +
                "type_crypto VARCHAR(10) NOT NULL," +
                "solde DECIMAL(15,8) DEFAULT 0.0," +
                "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        String sqlTransaction = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id VARCHAR(36) PRIMARY KEY," +
                "adresse_source VARCHAR(50) NOT NULL," +
                "adresse_destination VARCHAR(50) NOT NULL," +
                "montant DECIMAL(15,8) NOT NULL," +
                "frais DECIMAL(10,4) NOT NULL," +
                "niveau_frais VARCHAR(15) NOT NULL," +
                "statut VARCHAR(15) NOT NULL," +
                "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "type_crypto VARCHAR(10) NOT NULL," +
                "FOREIGN KEY (adresse_source) REFERENCES wallets(adresse)" +
                ")";

        try (Connection connexion = configBaseDeDonnees.getConnexion();
             Statement statement = connexion.createStatement()) {
            
            statement.execute(sqlWallet);
            statement.execute(sqlTransaction);
            System.out.println("Tables créées avec succès!");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la création des tables: " + e.getMessage());
        }
    }
}