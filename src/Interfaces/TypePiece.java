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
                        new Cords(3, 0), new Cords(4, 0),
                        new Cords(5, 0), new Cords(6, 0)
                );
            }
            case J -> {
                return List.of(
                        new Cords(3, 0), new Cords(3, 1),
                        new Cords(4, 1), new Cords(5, 1)
                );
            }
            case L -> {
                return List.of(
                        new Cords(5, 0), new Cords(5, 1),
                        new Cords(4, 1), new Cords(3, 1)
                );
            }
            case O -> {
                return List.of(
                        new Cords(4, 0), new Cords(5, 0),
                        new Cords(4, 1), new Cords(5, 1)
                );
            }
            case S -> {
                return List.of(
                        new Cords(3, 1), new Cords(4, 1),
                        new Cords(4, 0), new Cords(5, 0)
                );
            }
            case Z -> {
                return List.of(
                        new Cords(3, 0), new Cords(4, 0),
                        new Cords(4, 1), new Cords(5, 1)
                );
            }
            case T -> {
                return List.of(
                        new Cords(4, 0), new Cords(3, 1),
                        new Cords(4, 1), new Cords(5, 1)
                );
            }
            default -> {}
        }
        return null;
    }
}
