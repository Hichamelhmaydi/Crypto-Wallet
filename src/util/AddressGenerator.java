package util;

import domain.enums.CryptoType;
import java.security.SecureRandom;

public class AddressGenerator {
    private static final SecureRandom random = new SecureRandom();

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b)); 
        }
        return sb.toString();
    }

    public static String genererAdresse(CryptoType typeCrypto) {
        byte[] bytes = new byte[20]; 
        random.nextBytes(bytes);

        String hex = bytesToHex(bytes);

        switch (typeCrypto) {
            case ETHEREUM:
                return "0x" + hex;
            case BITCOIN:
              return "1" + hex.substring(0, 39);
            default:
                throw new IllegalArgumentException("Type de crypto non supporté: " + typeCrypto);
        }
    }

    public static boolean validerAdresse(String adresse, CryptoType typeCrypto) {
        if (adresse == null || adresse.trim().isEmpty()) {
            return false;
        }

        switch (typeCrypto) {
            case ETHEREUM:
                return adresse.startsWith("0x") && adresse.length() == 42;
            case BITCOIN:
                return adresse.startsWith("1") || adresse.startsWith("3") || adresse.startsWith("bc1");
            default:
                return false;
        }
    }
}
