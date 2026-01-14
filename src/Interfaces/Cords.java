package Interfaces;

public class Cords {
    public Integer x = 0;
    public Integer y = 0;
    public Cords(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Cords rotateCord(Cords pivot ) {
        var xRel = x - pivot.x;
        var yRel = y - pivot.y;

        var xRotateRel = -1 * yRel;

        var xAbs = xRotateRel + pivot.x;
        var yAbs = xRel + pivot.y;

        return new Cords(xAbs,yAbs);
    }
}