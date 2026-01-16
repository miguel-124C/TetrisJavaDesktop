import Presentation.TetrisFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi I'm Tetris");
        SwingUtilities.invokeLater(() -> {
            TetrisFrame frame = new TetrisFrame();
            frame.setVisible(true);
        });
    }

}
