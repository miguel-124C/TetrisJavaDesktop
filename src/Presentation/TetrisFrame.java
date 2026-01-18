package Presentation;

import Classes.Display;
import Classes.Matrix;
import Interfaces.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TetrisFrame extends JFrame {
    private final Matrix matrix = new Matrix(20, 10, Colors.BLACK);
    private final Display display = new Display();
    private final MusicPlayer musicPlayer = new MusicPlayer();
    private JPanel mainPanel;
    private final TetrisPanel tetrisPanel;
    private final SidePanel sidePanel;

    public TetrisFrame() {
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Crear el panel principal
        tetrisPanel = new TetrisPanel(matrix, display);
        // Crear panel lateral con información
        sidePanel = new SidePanel(display);

        // Usar BorderLayout para organizar
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(30, 30, 30));

        mainPanel.add(tetrisPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.EAST);

        addEvents(mainPanel);
        // Asegurar que el panel tenga foco
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                mainPanel.requestFocusInWindow();
            }
        });

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);

        startGame();
    }

    private void addEvents( JPanel gamePanel ) {
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                var currentPiece = display.getCurrentPiece();
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        currentPiece.rotate();
                        break;
                    case KeyEvent.VK_DOWN:
                        currentPiece.MoveDown();
                        display.setScore(display.getScore() + 1);
                        sidePanel.updatePanel();
                        break;
                    case KeyEvent.VK_LEFT:
                        if (matrix.canMoveX( currentPiece, false )) {
                            currentPiece.Move(false);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (matrix.canMoveX( currentPiece, true )) {
                            currentPiece.Move(true);
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        try {
                            matrix.moveToShadow(display);
                            sidePanel.updatePanel();
                            display.changePiece();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
                tetrisPanel.updateMatrix();
            }
        });
    }

    private void startGame() {
        musicPlayer.reproduce("assets/tetrisTheme.wav");
        display.changePiece();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            sidePanel.updatePanel();
            var currentPiece = display.getCurrentPiece();
            if (!currentPiece.canMove) {
                display.changePiece();
            }
            display.getCurrentPiece().MoveDown();

            tetrisPanel.updateMatrix();

            checkGameOver(scheduler);
        };

        scheduler.scheduleAtFixedRate(task, 1, 1000, TimeUnit.MILLISECONDS);
    }

    public void checkGameOver(ScheduledExecutorService scheduler) {
        if (matrix.getHeightValues() == 0) {
            scheduler.shutdown();
            showGameOverDialog(this);
        }
    }
    private void showGameOverDialog(JFrame parentFrame) {
        musicPlayer.getClip().close();
        var confirmDialog = JOptionPane.showConfirmDialog(
                parentFrame,
                "¡Game Over!, Try again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        switch (confirmDialog) {
            case JOptionPane.YES_OPTION:
//                restartGame(parentFrame);
                break;

            case JOptionPane.NO_OPTION:
            case JOptionPane.CLOSED_OPTION:
                parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                parentFrame.dispose();
                break;
        }
    }
}