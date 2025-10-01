package repository;

import domain.Portefeuille;
import domain.enums.TypeCrypto;
import config.DatabaseConfig;
import config.SQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des portefeuilles en base de données
 * Implémente les opérations CRUD pour les portefeuilles
 */
public class PortefeuilleRepository implements Repository<Portefeuille, String> {
    
    private final DatabaseConfig configBaseDeDonnees;
    
    public PortefeuilleRepository() {
        this.configBaseDeDonnees = new DatabaseConfig();
    }
    
    @Override
    public Optional<Portefeuille> trouverParId(String id) {
        try (Connection connexion = configBaseDeDonnees.getConnection();
             PreparedStatement statement = connexion.prepareStatement(SQL.SELECTIONNER_PORTEFEUILLE_PAR_ID)) {
            
            statement.setString(1, id);
            ResultSet resultat = statement.executeQuery();
            
            if (resultat.next()) {
                return Optional.of(creerPortefeuilleDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du portefeuille: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    @Override
    public List<Portefeuille> trouverTous() {
        List<Portefeuille> portefeuilles = new ArrayList<>();
        
        try (Connection connexion = configBaseDeDonnees.getConnection();
             Statement statement = connexion.createStatement();
             ResultSet resultat = statement.executeQuery(SQL.SELECTIONNER_TOUS_PORTEFEUILLES)) {
            
            while (resultat.next()) {
                portefeuilles.add(creerPortefeuilleDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des portefeuilles: " + e.getMessage());
        }
        return portefeuilles;
    }
    
    @Override
    public Portefeuille sauvegarder(Portefeuille portefeuille) {
        try (Connection connexion = configBaseDeDonnees.getConnection();
             PreparedStatement statement = connexion.prepareStatement(SQL.INSERER_PORTEFEUILLE)) {
            
            statement.setString(1, portefeuille.getId());
            statement.setString(2, portefeuille.getTypeCrypto().name());
            statement.setString(3, portefeuille.getAdresse());
            statement.setDouble(4, portefeuille.getSolde());
            
            statement.executeUpdate();
            return portefeuille;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du portefeuille: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void supprimerParId(String id) {
        try (Connection connexion = configBaseDeDonnees.getConnection();
             PreparedStatement statement = connexion.prepareStatement("DELETE FROM portefeuilles WHERE id = ?")) {
            
            statement.setString(1, id);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du portefeuille: " + e.getMessage());
        }
    }
    
    private Portefeuille creerPortefeuilleDepuisResultSet(ResultSet resultat) throws SQLException {
        return new Portefeuille(
            resultat.getString("id"),
            TypeCrypto.valueOf(resultat.getString("type_crypto")),
            resultat.getString("adresse"),
            resultat.getDouble("solde"),
            resultat.getTimestamp("date_creation").toLocalDateTime()
        );
    }
}