package Presentation.panel;

import Classes.Matrix;
import Classes.Piece;
import Interfaces.Colors;

import java.awt.*;

public class PanelDraw {
    private final int cellSize;
    public PanelDraw(int cellSize) {
        this.cellSize = cellSize;
    }
    private void drawBlock(Graphics2D g2d, int row, int col, Color color) {
        int x = row * cellSize;
        int y = col * cellSize;

        // Dibujar bloque principal con gradiente
        GradientPaint gradient = new GradientPaint(
                x, y, color.brighter(),
                x + cellSize, y + cellSize, color.darker()
        );
        g2d.setPaint(gradient);
        g2d.fillRect(x + 1, y + 1, cellSize - 2, cellSize - 2);

        // Borde interior
        g2d.setColor(color.brighter().brighter());
        g2d.drawRect(x + 1, y + 1, cellSize - 3, cellSize - 3);

        // Efecto 3D
        g2d.setColor(color.brighter());
        g2d.drawLine(x + 1, y + 1, x + cellSize - 2, y + 1); // Borde superior
        g2d.drawLine(x + 1, y + 1, x + 1, y + cellSize - 2); // Borde izquierdo

        g2d.setColor(color.darker().darker());
        g2d.drawLine(x + cellSize - 2, y + 1, x + cellSize - 2, y + cellSize - 2); // Borde derecho
        g2d.drawLine(x + 1, y + cellSize - 2, x + cellSize - 2, y + cellSize - 2); // Borde inferior
    }

    public void drawCurrentPiece(Graphics2D g2d, Piece currentPiece) {
        var pieceColor = currentPiece.getColor();
        currentPiece.getCords().forEach( c -> {
            drawBlock(g2d, c.x, c.y, Colors.getColor(pieceColor));
        });
    }

    private void drawGrid(Graphics2D g2d, Matrix matrix) throws Exception {
        g2d.setColor(new Color(40, 40, 40));

        // Líneas verticales
        for (int i = 0; i <= matrix.getCOL(); i++) {
            int x = i * cellSize;
            g2d.drawLine(x, 0, x, matrix.getROW() * cellSize);
        }

        // Líneas horizontales
        for (int i = 0; i <= matrix.getROW(); i++) {
            int y = i * cellSize;
            g2d.drawLine(0, y, matrix.getCOL() * cellSize, y);
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                var value = matrix.getValue(i, j);
                drawBlock( g2d, i, j, Colors.getColor( value ) );
            }
        }
    }
}
