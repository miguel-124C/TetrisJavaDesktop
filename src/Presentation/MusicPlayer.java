package Presentation;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {

    public void reproduce(String pathFile) {
        try {
            File fileMusic = new File(pathFile);

            if (fileMusic.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(fileMusic);
                Clip clip = AudioSystem.getClip();

                clip.open(audioInput);
                clip.start();

                clip.loop(Clip.LOOP_CONTINUOUSLY);
                
            } else {
                System.out.println("No se encuentra el archivo en la ruta: " + pathFile);
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
