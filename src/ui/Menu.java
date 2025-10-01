package ui;

import domain.Portefeuille;
import domain.Transaction;
import domain.enums.TypeCrypto;
import domain.enums.NiveauFrais;
import service.ServicePortefeuille;
import service.ServiceTransaction;
import service.ServiceMempool;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;


public class Menu {
    
    private final ServicePortefeuille servicePortefeuille;
    private final ServiceTransaction serviceTransaction;
    private final ServiceMempool serviceMempool;
    private final Scanner scanner;
    private Portefeuille portefeuilleActuel;
    
    public Menu() {
        this.servicePortefeuille = new ServicePortefeuille();
        this.serviceTransaction = new ServiceTransaction();
        this.serviceMempool = new ServiceMempool();
        this.scanner = new Scanner(System.in);
    }
    
    public void demarrer() {
        System.out.println("=== Simulateur de Crypto Wallet ===");
        
        while (true) {
            afficherMenuPrincipal();
            String choix = scanner.nextLine();
            
            switch (choix) {
                case "1":
                    creerPortefeuille();
                    break;
                case "2":
                    creerTransaction();
                    break;
                case "3":
                    voirPositionMempool();
                    break;
                case "4":
                    comparerNiveauxFrais();
                    break;
                case "5":
                    consulterMempool();
                    break;
                case "6":
                    listerPortefeuilles();
                    break;
                case "0":
                    System.out.println("Au revoir!");
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
    
    private void afficherMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Créer un portefeuille crypto");
        System.out.println("2. Créer une nouvelle transaction");
        System.out.println("3. Voir ma position dans le mempool");
        System.out.println("4. Comparer les 3 niveaux de frais");
        System.out.println("5. Consulter l'état actuel du mempool");
        System.out.println("6. Lister tous les portefeuilles");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }
    
    private void creerPortefeuille() {
        System.out.println("\n=== CRÉATION DE PORTEFEUILLE ===");
        System.out.println("Choisissez le type de cryptomonnaie:");
        System.out.println("1. Bitcoin");
        System.out.println("2. Ethereum");
        System.out.print("Votre choix: ");
        
        String choix = scanner.nextLine();
        TypeCrypto typeCrypto = null;
        
        switch (choix) {
            case "1":
                typeCrypto = TypeCrypto.BITCOIN;
                break;
            case "2":
                typeCrypto = TypeCrypto.ETHEREUM;
                break;
            default:
                System.out.println("Type invalide.");
                return;
        }
        
        Portefeuille portefeuille = servicePortefeuille.creerPortefeuille(typeCrypto);
        this.portefeuilleActuel = portefeuille;
        
        System.out.println(" Portefeuille créé avec succès!");
        System.out.println("ID: " + portefeuille.getId());
        System.out.println("Adresse: " + portefeuille.getAdresse());
        System.out.println("Type: " + portefeuille.getTypeCrypto());
    }
    
    private void creerTransaction() {
        if (portefeuilleActuel == null) {
            System.out.println(" Veuillez d'abord créer ou sélectionner un portefeuille.");
            return;
        }
        
        System.out.println("\n=== CRÉATION DE TRANSACTION ===");
        System.out.print("Adresse destination: ");
        String adresseDestination = scanner.nextLine();
        
        System.out.print("Montant: ");
        double montant = Double.parseDouble(scanner.nextLine());
        
        System.out.println("Niveau de frais:");
        System.out.println("1. Économique");
        System.out.println("2. Standard");
        System.out.println("3. Rapide");
        System.out.print("Votre choix: ");
        
        String choixFrais = scanner.nextLine();
        NiveauFrais niveauFrais = null;
        
        switch (choixFrais) {
            case "1":
                niveauFrais = NiveauFrais.ECONOMIQUE;
                break;
            case "2":
                niveauFrais = NiveauFrais.STANDARD;
                break;
            case "3":
                niveauFrais = NiveauFrais.RAPIDE;
                break;
            default:
                System.out.println("Niveau invalide.");
                return;
        }
        
        Transaction transaction = serviceTransaction.creerTransaction(
            portefeuilleActuel.getId(),
            portefeuilleActuel.getAdresse(),
            adresseDestination,
            montant,
            niveauFrais,
            portefeuilleActuel.getTypeCrypto()
        );
        
        System.out.println("Transaction créée avec succès!");
        System.out.println("ID: " + transaction.getId());
        System.out.println("Frais: " + transaction.getFrais() + "$");
        System.out.println("Statut: " + transaction.getStatut());
    }
    
    private void voirPositionMempool() {
        System.out.println("\nFonctionnalité en développement...");
    }
    
    private void comparerNiveauxFrais() {
        System.out.println("\nFonctionnalité en développement...");
    }
    
    private void consulterMempool() {
        System.out.println("\n=== ÉTAT DU MEMPOOL ===");
        List<Transaction> mempool = serviceMempool.obtenirMempool();
        
        System.out.println("Transactions en attente: " + mempool.size());
        System.out.println("\n| Transaction | Frais |");
        System.out.println("|-------------|-------|");
        
        for (Transaction tx : mempool) {
            String affichage = String.format("| %s... | %.2f$ |", 
                tx.getAdresseSource().substring(0, 8), tx.getFrais());
            
            if (portefeuilleActuel != null && tx.getIdPortefeuille().equals(portefeuilleActuel.getId())) {
                affichage = ">>> VOTRE TX: " + affichage;
            }
            
            System.out.println(affichage);
        }
    }
    
    private void listerPortefeuilles() {
        System.out.println("\n=== LISTE DES PORTEFEUILLES ===");
        List<Portefeuille> portefeuilles = servicePortefeuille.obtenirTousLesPortefeuilles();
        
        if (portefeuilles.isEmpty()) {
            System.out.println("Aucun portefeuille trouvé.");
            return;
        }
        
        for (Portefeuille p : portefeuilles) {
            System.out.println(p);
        }
    }
    
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.demarrer();
    }
}