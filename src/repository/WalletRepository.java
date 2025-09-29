package repository;

import domain.Wallet;
import domain.enums.CryptoType;
import config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WalletRepository implements Repository<Wallet, String> {
    private final DatabaseConfig configBaseDeDonnees;

    public WalletRepository() {
        this.configBaseDeDonnees = DatabaseConfig.getInstance();
    }

    @Override
    public Optional<Wallet> trouverParId(String id) {
        String sql = "SELECT * FROM wallets WHERE id = ?";
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            
            statement.setString(1, id);
            ResultSet resultat = statement.executeQuery();
            
            if (resultat.next()) {
                return Optional.of(creerWalletDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du wallet: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Wallet> trouverParAdresse(String adresse) {
        String sql = "SELECT * FROM wallets WHERE adresse = ?";
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            
            statement.setString(1, adresse);
            ResultSet resultat = statement.executeQuery();
            
            if (resultat.next()) {
                return Optional.of(creerWalletDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du wallet par adresse: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Wallet> trouverTous() {
        List<Wallet> wallets = new ArrayList<>();
        String sql = "SELECT * FROM wallets";
        
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             Statement statement = connexion.createStatement();
             ResultSet resultat = statement.executeQuery(sql)) {
            
            while (resultat.next()) {
                wallets.add(creerWalletDepuisResultSet(resultat));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des wallets: " + e.getMessage());
        }
        return wallets;
    }

    @Override
    public Wallet sauvegarder(Wallet wallet) {
        String sql = "INSERT INTO wallets (id, adresse, type_crypto, solde) VALUES (?, ?, ?, ?)";
        
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            
            statement.setString(1, wallet.getId());
            statement.setString(2, wallet.getAdresse());
            statement.setString(3, wallet.getTypeCrypto().name());
            statement.setBigDecimal(4, wallet.getSolde());
            
            statement.executeUpdate();
            return wallet;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du wallet: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void supprimer(String id) {
        String sql = "DELETE FROM wallets WHERE id = ?";
        
        try (Connection connexion = configBaseDeDonnees.getConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            
            statement.setString(1, id);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du wallet: " + e.getMessage());
        }
    }

    private Wallet creerWalletDepuisResultSet(ResultSet resultat) throws SQLException {
        Wallet wallet = new Wallet(
            resultat.getString("adresse"),
            CryptoType.valueOf(resultat.getString("type_crypto"))
        );
        wallet.setSolde(resultat.getBigDecimal("solde"));
        return wallet;
    }
}