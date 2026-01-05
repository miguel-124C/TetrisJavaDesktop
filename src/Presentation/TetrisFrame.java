package Presentation;

import Classes.Display;
import Classes.Matrix;
import Interfaces.Colors;
import Presentation.panel.TetrisPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TetrisFrame extends JFrame {
    private final Matrix matrix = new Matrix(20, 10, Colors.BLACK);
    private final Display display = new Display();
    private final TetrisPanel tetrisPanel;
    private final SidePanel sidePanel;
    public TetrisFrame() {
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        display.changePiece();
        // Crear el panel principal
        tetrisPanel = new TetrisPanel(matrix, display);
        // Crear panel lateral con información
        sidePanel = new SidePanel(display);

        // Usar BorderLayout para organizar
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
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
        setLocationRelativeTo(null); // Centrar en pantalla

        display.changePiece();
        startGame();
    }

    private void addEvents( JPanel gamePanel ) {
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                var currentPiece = display.getCurrentPiece();
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        System.out.println("ARRIBA presionado");
                        break;
                    case KeyEvent.VK_DOWN:
                        currentPiece.MoveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        currentPiece.Move(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        currentPiece.Move(true);
                        break;
                }
            }
        });
    }

    private void startGame() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            private int count = 0;

            @Override
            public void run() {
                display.setScore( display.getScore() + 1 );
                display.setLevel( display.getLevel() + 1 );

                sidePanel.updatePanel();

                tetrisPanel.updateMatrix();

                System.out.println("Tarea ejecutada: " + (++count));

                if (count >= 500) {
                    scheduler.shutdown();
                }
            }
        };

        // Programar tarea cada 1 segundo
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

}