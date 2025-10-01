package util;

import domain.enums.TypeCrypto;
import java.security.SecureRandom;
import java.util.Random;

public class GenerateurAdresse {
    
    private static final String CARACTERES_HEXA = "0123456789abcdef";
    private static final String CARACTERES_BITCOIN = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final Random random = new SecureRandom();
    
    public static String genererAdresse(TypeCrypto typeCrypto) {
        switch (typeCrypto) {
            case ETHEREUM:
                return genererAdresseEthereum();
            case BITCOIN:
                return genererAdresseBitcoin();
            default:
                throw new IllegalArgumentException("Type de crypto non supporté: " + typeCrypto);
        }
    }
    
    private static String genererAdresseEthereum() {
        StringBuilder adresse = new StringBuilder("0x");
        for (int i = 0; i < 40; i++) {
            adresse.append(CARACTERES_HEXA.charAt(random.nextInt(CARACTERES_HEXA.length())));
        }
        return adresse.toString();
    }
    
    private static String genererAdresseBitcoin() {
        StringBuilder adresse = new StringBuilder();
        String[] prefixes = {"1", "3", "bc1"};
        String prefix = prefixes[random.nextInt(prefixes.length)];
        adresse.append(prefix);
        
        int longueur = prefix.equals("bc1") ? 39 : 33;
        for (int i = prefix.length(); i < longueur; i++) {
            adresse.append(CARACTERES_BITCOIN.charAt(random.nextInt(CARACTERES_BITCOIN.length())));
        }
        return adresse.toString();
    }
}