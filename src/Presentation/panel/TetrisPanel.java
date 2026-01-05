package Presentation.panel;

import Classes.Display;
import Classes.Matrix;

import javax.swing.*;
import java.awt.*;

public class TetrisPanel extends JPanel {
    private final Matrix matrix;
    private final Display display;
    private static final int CELL_SIZE = 30;
    private final PanelDraw panelDraw = new PanelDraw(CELL_SIZE);

    public TetrisPanel(Matrix matrix, Display display) {
        this.matrix = matrix;
        this.display = display;

        setPreferredSize(new Dimension(matrix.getCOL() * CELL_SIZE, matrix.getROW() * CELL_SIZE));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar la cuadrícula
//        drawGrid(g2d);
        // Dibujar la pieza actual (fantasma)
        panelDraw.drawCurrentPiece(g2d, display.getCurrentPiece());
    }

    public void updateMatrix() {
        repaint();
    }
}