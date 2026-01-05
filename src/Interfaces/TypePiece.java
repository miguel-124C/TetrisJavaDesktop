package Interfaces;

import java.util.concurrent.ThreadLocalRandom;

public enum TypePiece {
    I, J, L, O, S, Z, T;

    public static TypePiece random() {
        TypePiece[] types = values();
        int index = ThreadLocalRandom.current().nextInt(types.length);
        return types[index];
    }
}
