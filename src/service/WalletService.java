package service;

import domain.Wallet;
import domain.enums.CryptoType;
import repository.WalletRepository;
import util.AddressGenerator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService() {
        this.walletRepository = new WalletRepository();
    }

    public Wallet creerWallet(CryptoType typeCrypto) {
        String adresse = AddressGenerator.genererAdresse(typeCrypto);
        Wallet wallet = new Wallet(adresse, typeCrypto);
        return walletRepository.sauvegarder(wallet);
    }

    public Optional<Wallet> trouverWalletParAdresse(String adresse) {
        return walletRepository.trouverParAdresse(adresse);
    }

    public List<Wallet> listerTousLesWallets() {
        return walletRepository.trouverTous();
    }

    public boolean mettreAJourSolde(String adresse, BigDecimal nouveauSolde) {
        Optional<Wallet> walletOpt = walletRepository.trouverParAdresse(adresse);
        if (walletOpt.isPresent()) {
            Wallet wallet = walletOpt.get();
            wallet.setSolde(nouveauSolde);
            walletRepository.sauvegarder(wallet);
            return true;
        }
        return false;
    }
}