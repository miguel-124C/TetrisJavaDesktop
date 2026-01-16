package Presentation;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private Clip clip;

    public void reproduce(String pathFile) {
        try {
            File fileMusic = new File(pathFile);
            System.out.println("Java está buscando en: " + fileMusic.getAbsolutePath());

            if (fileMusic.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(fileMusic);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();

            } else {
                System.out.println("No se encuentra el archivo en la ruta: " + pathFile);
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public Clip getClip() {
        return clip;
    }
}
