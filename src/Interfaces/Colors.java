package Interfaces;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public enum Colors {
    BLACK, CYAN, BLUE, ORANGE, YELLOW, GREEN, PURPLE, RED;

    public static Colors random() {
        Colors[] types = values();
        int index = ThreadLocalRandom.current().nextInt(types.length);
        return types[index];
    }
    public static Color getColor( Colors color ) {
        if ( color == Colors.BLACK) return Color.BLACK;
        if ( color == Colors.YELLOW ) return Color.YELLOW;
        if ( color == Colors.GREEN ) return Color.GREEN;
        if ( color == Colors.BLUE ) return Color.BLUE;
        if ( color == Colors.CYAN) return Color.CYAN;
        if ( color == Colors.RED ) return Color.RED;
        if ( color == Colors.PURPLE ) return Color.MAGENTA;
        if ( color == Colors.ORANGE ) return Color.ORANGE;

        return Color.BLACK;
    }
}
