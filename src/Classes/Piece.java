package Classes;

import Interfaces.Colors;
import Interfaces.Cords;
import Interfaces.TypePiece;

import java.awt.*;
import java.util.List;

public class Piece {
    private final List<Cords> cords;
    private final Colors color;
    private final TypePiece typePiece;
    public boolean canMove = true;
    private Piece(List<Cords> cords, Colors color, TypePiece type) {
        this.color = color;
        this.cords = cords;
        this.typePiece = type;
    }
    public static Piece createRandomPiece(Colors color, TypePiece type) {
        var cords = getCordsByType(type);
        return new Piece(cords, color, type);
    }

    public static List<Cords> getCordsByType( TypePiece type ) {
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

    /* Direction: Right = True, Left = False */
    public void Move( boolean direction ) {
        if (!canMove) return;
        cords.forEach(c -> c.x += (direction) ? 1 : -1);
    }

    public void MoveDown() {
        if (!canMove) return;
        cords.forEach(c -> c.y += 1);
    }

    public List<Cords> getCords() {
        return cords;
    }
    public TypePiece getTypePiece() { return typePiece; }
    public Colors getColor() { return color; }
}
