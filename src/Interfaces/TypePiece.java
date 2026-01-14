package Interfaces;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum TypePiece {
    I, J, L, O, S, Z, T;

    public static TypePiece random() {
        TypePiece[] types = values();
        int index = ThreadLocalRandom.current().nextInt(types.length);
        return types[index];
    }

    public static List<Cords> getCordsByType(TypePiece type ) {
        switch ( type ) {
            case I -> {
                return List.of(
                        new Cords(0, 0), new Cords(0, 1),
                        new Cords(0, 2), new Cords(0, 3)
                );
            }
            case J -> {
                return List.of(
                        new Cords(1, 0), new Cords(1, 1),
                        new Cords(1, 2), new Cords(0, 2)
                );
            }
            case L -> {
                return List.of(
                        new Cords(0, 0), new Cords(0, 1),
                        new Cords(0, 2), new Cords(1, 2)
                );
            }
            case O -> {
                return List.of(
                        new Cords(0, 0), new Cords(1, 0),
                        new Cords(0, 1), new Cords(1, 1)
                );
            }
            case S -> {
                return List.of(
                        new Cords(0, 1), new Cords(1, 1),
                        new Cords(1, 0), new Cords(2, 0)
                );
            }
            case Z -> {
                return List.of(
                        new Cords(0, 0), new Cords(1, 0),
                        new Cords(1, 1), new Cords(2, 1)
                );
            }
            case T -> {
                return List.of(
                        new Cords(1, 0), new Cords(0, 1),
                        new Cords(1, 1), new Cords(2, 1)
                );
            }
            default -> {}
        }
        return null;
    }
}
