package Presentation;

import Classes.Display;
import Classes.Matrix;

import javax.swing.*;
import java.awt.*;

public class TetrisPanel extends JPanel {
    private final Matrix matrix;
    private final Display display;
    private static final int CELL_SIZE = 30;

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

        try {
            matrix.drawGrid(g2d);
            matrix.drawAllPieces(g2d);
            matrix.drawCurrentPiece(g2d, display);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMatrix() {
        repaint();
    }
}