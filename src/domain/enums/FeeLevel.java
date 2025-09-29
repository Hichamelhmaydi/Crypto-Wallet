package domain.enums;

public enum FeeLevel {
    ECONOMIQUE(1),
    STANDARD(2),
    RAPIDE(3);

    private final int multiplicateur;

    FeeLevel(int multiplicateur) {
        this.multiplicateur = multiplicateur;
    }

    public int getMultiplicateur() {
        return multiplicateur;
    }
}