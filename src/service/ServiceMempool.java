package service;

import domain.Transaction;
import repository.TransactionRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceMempool {
    
    private final TransactionRepository transactionRepository;
    
    public ServiceMempool() {
        this.transactionRepository = new TransactionRepository();
    }
    
    public List<Transaction> obtenirMempool() {
        return transactionRepository.trouverTous().stream()
            .filter(t -> t.getStatut().name().equals("EN_ATTENTE"))
            .sorted(Comparator.comparingDouble(Transaction::getFrais).reversed())
            .collect(Collectors.toList());
    }
    
    public int calculerPositionDansMempool(double fraisTransaction) {
        List<Transaction> mempool = obtenirMempool();
        
        for (int i = 0; i < mempool.size(); i++) {
            if (fraisTransaction >= mempool.get(i).getFrais()) {
                return i + 1;
            }
        }
        return mempool.size() + 1;
    }
    
    public long estimerTempsAttente(int position) {
        return position * 10L; 
    }
    
    public void genererTransactionsAleatoires(int nombre) {
        // Implémentation pour générer des transactions aléatoires
        // pour simuler l'activité du réseau
    }
}