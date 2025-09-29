package repository;

import domain.Transaction;
import domain.enums.CryptoType;
import domain.enums.FeeLevel;
import domain.enums.TransactionStatus;
import config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepository implements Repository<Transaction, String> {
    private final DatabaseConfig configBaseDeDonnees;

    public TransactionRepository() {
        this.configBaseDeDonnees = DatabaseConfig.getInstance();
    }

    @Override
    public Optional<Transaction> trouverParId(String id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            
            statement.setString(1, id);
            ResultSet resultat = statement.executeQuery();
            
            if (resultat.next()) {
                return Optional.of(creerTransactionDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la transaction: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Transaction> trouverParAdresseSource(String adresseSource) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE adresse_source = ?";
        
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            
            statement.setString(1, adresseSource);
            ResultSet resultat = statement.executeQuery();
            
            while (resultat.next()) {
                transactions.add(creerTransactionDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des transactions: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public List<Transaction> trouverTous() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date_creation DESC";
        
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             Statement statement = connexion.createStatement();
             ResultSet resultat = statement.executeQuery(sql)) {
            
            while (resultat.next()) {
                transactions.add(creerTransactionDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des transactions: " + e.getMessage());
        }
        return transactions;
    }

    public List<Transaction> trouverEnAttente() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE statut = 'EN_ATTENTE' ORDER BY frais DESC";
        
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             Statement statement = connexion.createStatement();
             ResultSet resultat = statement.executeQuery(sql)) {
            
            while (resultat.next()) {
                transactions.add(creerTransactionDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des transactions en attente: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public Transaction sauvegarder(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, adresse_source, adresse_destination, montant, " +
                    "frais, niveau_frais, statut, type_crypto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            
            statement.setString(1, transaction.getId());
            statement.setString(2, transaction.getAdresseSource());
            statement.setString(3, transaction.getAdresseDestination());
            statement.setBigDecimal(4, transaction.getMontant());
            statement.setBigDecimal(5, transaction.getFrais());
            statement.setString(6, transaction.getNiveauFrais().name());
            statement.setString(7, transaction.getStatut().name());
            statement.setString(8, transaction.getTypeCrypto().name());
            
            statement.executeUpdate();
            return transaction;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde de la transaction: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void supprimer(String id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            
            statement.setString(1, id);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la transaction: " + e.getMessage());
        }
    }

    private Transaction creerTransactionDepuisResultSet(ResultSet resultat) throws SQLException {
        Transaction transaction = new Transaction(
            resultat.getString("adresse_source"),
            resultat.getString("adresse_destination"),
            resultat.getBigDecimal("montant"),
            resultat.getBigDecimal("frais"),
            FeeLevel.valueOf(resultat.getString("niveau_frais")),
            CryptoType.valueOf(resultat.getString("type_crypto"))
        );
        transaction.setStatut(TransactionStatus.valueOf(resultat.getString("statut")));
        return transaction;
    }
}