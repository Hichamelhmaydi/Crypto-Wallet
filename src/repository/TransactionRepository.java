package repository;

import domain.Transaction;
import domain.enums.StatutTransaction;
import domain.enums.NiveauFrais;
import config.DatabaseConfig;
import config.SQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TransactionRepository implements Repository<Transaction, String> {
    
    private final DatabaseConfig configBaseDeDonnees;
    
    public TransactionRepository() {
        this.configBaseDeDonnees = new DatabaseConfig();
    }
    
    @Override
    public Optional<Transaction> trouverParId(String id) {
       
        return Optional.empty();
    }
    
    @Override
    public List<Transaction> trouverTous() {
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection connexion = configBaseDeDonnees.getConnection();
             Statement statement = connexion.createStatement();
             ResultSet resultat = statement.executeQuery(SQL.SELECTIONNER_TOUTES_TRANSACTIONS)) {
            
            while (resultat.next()) {
                transactions.add(creerTransactionDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des transactions: " + e.getMessage());
        }
        return transactions;
    }
    
    public List<Transaction> trouverParPortefeuille(String idPortefeuille) {
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection connexion = configBaseDeDonnees.getConnection();
             PreparedStatement statement = connexion.prepareStatement(SQL.SELECTIONNER_TRANSACTIONS_PAR_PORTEFEUILLE)) {
            
            statement.setString(1, idPortefeuille);
            ResultSet resultat = statement.executeQuery();
            
            while (resultat.next()) {
                transactions.add(creerTransactionDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des transactions: " + e.getMessage());
        }
        return transactions;
    }
    
    @Override
    public Transaction sauvegarder(Transaction transaction) {
        try (Connection connexion = configBaseDeDonnees.getConnection();
             PreparedStatement statement = connexion.prepareStatement(SQL.INSERER_TRANSACTION)) {
            
            statement.setString(1, transaction.getId());
            statement.setString(2, transaction.getIdPortefeuille());
            statement.setString(3, transaction.getAdresseSource());
            statement.setString(4, transaction.getAdresseDestination());
            statement.setDouble(5, transaction.getMontant());
            statement.setDouble(6, transaction.getFrais());
            statement.setString(7, transaction.getNiveauFrais().name());
            statement.setString(8, transaction.getStatut().name());
            
            statement.executeUpdate();
            return transaction;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde de la transaction: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void supprimerParId(String id) {
        // Implémentation de suppression
    }
    
    public void mettreAJourStatut(String idTransaction, StatutTransaction statut) {
        try (Connection connexion = configBaseDeDonnees.getConnection();
             PreparedStatement statement = connexion.prepareStatement(SQL.METTRE_A_JOUR_STATUT_TRANSACTION)) {
            
            statement.setString(1, statut.name());
            statement.setString(2, idTransaction);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du statut: " + e.getMessage());
        }
    }
    
    private Transaction creerTransactionDepuisResultSet(ResultSet resultat) throws SQLException {
        return new Transaction(
            resultat.getString("id"),
            resultat.getString("id_portefeuille"),
            resultat.getString("adresse_source"),
            resultat.getString("adresse_destination"),
            resultat.getDouble("montant"),
            resultat.getDouble("frais"),
            NiveauFrais.valueOf(resultat.getString("niveau_frais")),
            StatutTransaction.valueOf(resultat.getString("statut")),
            resultat.getTimestamp("date_creation").toLocalDateTime()
        );
    }
}