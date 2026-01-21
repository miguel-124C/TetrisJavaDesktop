package Classes;

import Interfaces.Colors;
import Interfaces.Cords;
import Interfaces.TypePiece;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    private List<Cords> cords;
    private Cords pivot;
    private List<Cords> cordsShadow;
    private boolean canDrawShadow = true;
    private final Colors color;
    private final TypePiece typePiece;
    public boolean canMove = true;
    // Cord for piece down
    private int cordYMay = 0;
    private int cordYMen = 100;
    private int cordXLeft = -1;
    private int cordXRight = 100;
    private Piece(List<Cords> cords, Colors color, TypePiece type) {
        this.color = color;
        this.cords = cords;
        moveAndSetCordXRightLeft( false, false );
        this.typePiece = type;
    }
    public static Piece createRandomPiece(Colors color, TypePiece type) {
        var cords = TypePiece.getCordsByType(type);
        return new Piece(cords, color, type);
    }

    /* Direction: Right = True, Left = False */
    public void Move( boolean direction ) {
        if (!canMove) return;
        moveAndSetCordXRightLeft( true, direction );
    }

    public void MoveDown() {
        if (!canMove) return;
        cords.forEach(c -> {
            c.y += 1;
            if ( c.y > cordYMay ) {
                cordYMay = c.y;
            }
        });
    }
    public void moveToShadow() {
        if (cordsShadow == null) return;
        List<Cords> newCords = new ArrayList<>(cordsShadow);
        for (int i = 0; i < cordsShadow.size(); i++) {
            newCords.set(i, cordsShadow.get(i));
        }

        cords = newCords;
        canDrawShadow = false;
    }
    private void moveAndSetCordXRightLeft( boolean move, boolean direction ) {
        var cordXMen = 100;
        var cordXMay = 0;
        for(var cord : cords) {
            if ( move ) {
                cord.x += (direction) ? 1 : -1;
            }
            if ( cord.x < cordXMen ) cordXMen = cord.x;
            if ( cord.x > cordXMay ) cordXMay = cord.x;
        }
        cordXLeft = cordXMen;
        cordXRight = cordXMay;
    }

    public void rotate() {
        if ( typePiece == TypePiece.O ) return;
        List<Cords> newList = new ArrayList<>(cords);
        var pivot = new Cords(cords.get(0).x, cords.get(0).y);
        for (int i = 1; i < cords.size(); i++) {
            var newCords = cords.get(i).rotateCord(pivot);
            newList.set(i, newCords);
        }
        cords = newList;
    }

    public int getCordYMay() { return cordYMay; }
    public int getCordYMen() { return cordYMen; }
    public void setCordYMen(int cordYMen) { this.cordYMen = cordYMen; }
    public int getCordXLeft() { return cordXLeft; }
    public int getCordXRight() { return cordXRight; }
    public List<Cords> getCords() {
        return cords;
    }
    public TypePiece getTypePiece() { return typePiece; }
    public Colors getColor() { return color; }
    public List<Cords> getCordsShadow() {
        return cordsShadow;
    }
    public void setCordsShadow(List<Cords> cordsShadow) {
        if (!canDrawShadow) return;
        this.cordsShadow = cordsShadow;

        var cordMenY = 900;
        for(var c : cordsShadow) {
            if ( c.y < cordMenY) cordMenY = c.y;
        }
        if ( this.getCordYMay() >= cordMenY ) canDrawShadow = false;
    }

    public boolean getCanDrawShadow() { return canDrawShadow; }
}
