package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static DatabaseConfig instance;
    private Connection connexion;
    private String url;
    private String utilisateur;
    private String motDePasse;

    private DatabaseConfig() {
        chargerConfiguration();
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    private void chargerConfiguration() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            Properties proprietes = new Properties();
            proprietes.load(input);
            
            this.url = proprietes.getProperty("database.url");
            this.utilisateur = proprietes.getProperty("database.user");
            this.motDePasse = proprietes.getProperty("database.password");
            
            // Charger le driver PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement de la configuration de la base de données", e);
        }
    }

    public Connection getConnexion() throws SQLException {
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
        }
        return connexion;
    }

    public void fermerConnexion() {
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
            }
        }
    }
}