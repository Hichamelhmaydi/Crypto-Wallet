package config;

import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private String url;
    private String utilisateur;
    private String motDePasse;

    public DatabaseConfig() {
        chargerConfiguration();
    }

    private void chargerConfiguration() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            Properties proprietes = new Properties();
            proprietes.load(input);

            this.url = proprietes.getProperty("url");
            this.utilisateur = proprietes.getProperty("username");
            this.motDePasse = proprietes.getProperty("password");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement de la configuration de la base de données", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, utilisateur, motDePasse);
    }
}