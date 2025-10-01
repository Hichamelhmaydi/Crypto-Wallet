package service;

import domain.Transaction;
import domain.enums.NiveauFrais;
import domain.enums.TypeCrypto;
import repository.TransactionRepository;
import util.CalculateurFrais;
import java.util.List;


public class ServiceTransaction {
    
    private final TransactionRepository transactionRepository;
    
    public ServiceTransaction() {
        this.transactionRepository = new TransactionRepository();
    }
    
    public Transaction creerTransaction(String idPortefeuille, String adresseSource, 
                                      String adresseDestination, double montant, 
                                      NiveauFrais niveauFrais, TypeCrypto typeCrypto) {
        
        double frais = CalculateurFrais.calculerFrais(typeCrypto, niveauFrais, montant);
        Transaction transaction = new Transaction(idPortefeuille, adresseSource, adresseDestination, montant, frais, niveauFrais);
        return transactionRepository.sauvegarder(transaction);
    }
    
    public List<Transaction> obtenirTransactionsParPortefeuille(String idPortefeuille) {
        return transactionRepository.trouverParPortefeuille(idPortefeuille);
    }
    
    public List<Transaction> obtenirToutesTransactions() {
        return transactionRepository.trouverTous();
    }
}