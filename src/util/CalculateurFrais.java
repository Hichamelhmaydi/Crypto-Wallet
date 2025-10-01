package util;

import domain.enums.TypeCrypto;
import domain.enums.NiveauFrais;
import java.util.Random;


public class CalculateurFrais {
    
    private static final Random random = new Random();
    
    private static final double TARIF_BITCOIN_ECONOMIQUE = 0.0001; 
    private static final double TARIF_BITCOIN_STANDARD = 0.0005;
    private static final double TARIF_BITCOIN_RAPIDE = 0.0010;
    
    private static final double PRIX_GAS_ETH_ECONOMIQUE = 20.0; 
    private static final double PRIX_GAS_ETH_STANDARD = 40.0;
    private static final double PRIX_GAS_ETH_RAPIDE = 100.0;
    
    public static double calculerFrais(TypeCrypto typeCrypto, NiveauFrais niveauFrais, double montant) {
        switch (typeCrypto) {
            case BITCOIN:
                return calculerFraisBitcoin(niveauFrais, montant);
            case ETHEREUM:
                return calculerFraisEthereum(niveauFrais, montant);
            default:
                throw new IllegalArgumentException("Type de crypto non supporté: " + typeCrypto);
        }
    }
    
    private static double calculerFraisBitcoin(NiveauFrais niveauFrais, double montant) {
        double tarifParByte = 0.0;
        int tailleTransaction = 250 + random.nextInt(150); 
        
        switch (niveauFrais) {
            case ECONOMIQUE:
                tarifParByte = TARIF_BITCOIN_ECONOMIQUE;
                break;
            case STANDARD:
                tarifParByte = TARIF_BITCOIN_STANDARD;
                break;
            case RAPIDE:
                tarifParByte = TARIF_BITCOIN_RAPIDE;
                break;
        }
        
        return tailleTransaction * tarifParByte * (1 + random.nextDouble() * 0.2); 
    }
    
    private static double calculerFraisEthereum(NiveauFrais niveauFrais, double montant) {
        double prixGas = 0.0;
        int limiteGas = 21000 + random.nextInt(15000); 
        
        switch (niveauFrais) {
            case ECONOMIQUE:
                prixGas = PRIX_GAS_ETH_ECONOMIQUE;
                break;
            case STANDARD:
                prixGas = PRIX_GAS_ETH_STANDARD;
                break;
            case RAPIDE:
                prixGas = PRIX_GAS_ETH_RAPIDE;
                break;
        }
        
        return (limiteGas * prixGas * 1e-9) * (1 + random.nextDouble() * 0.2); 
    }
}