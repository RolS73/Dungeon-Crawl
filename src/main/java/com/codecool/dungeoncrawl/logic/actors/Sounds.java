package com.codecool.dungeoncrawl.logic.actors;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sounds {
    private static Clip bgMusic; //ch0
    private static Clip eventMusic; //ch1

    public static void playBGsound(String fileName) {
        stopBGsound();
        play(fileName, 0);
    }

    public static void playEVSound(String fileName) {
        stopEVsound();
        play(fileName, 1);
    }

    public static void playSound(String fileName) {
        play(fileName, 2);
    }

    public static void stopBGsound() {
        if (bgMusic != null && bgMusic.isOpen() && bgMusic.isRunning()) {
            bgMusic.stop();
        }
    }

    public static void stopEVsound() {
        if (eventMusic != null && eventMusic.isOpen() && eventMusic.isRunning()) {
            eventMusic.stop();
        }
    }

    private static void play(String fileName, int channel) { //0=bg(loop), 1=event(loop) else sfx)
        try {
            URL file = Sounds.class.getResource("/" + fileName + ".wav");
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            switch (channel) {
                case 0:
                    gainControl.setValue(-18.0f);
                    bgMusic = clip;
                    bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    break;
                case 1:
                    gainControl.setValue(-15.0f);
                    eventMusic = clip;
                    eventMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    break;
                default:
                    gainControl.setValue(-12.0f);
                    clip.start();
                    break;
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println("Valami nem stimmel a hanggal! :S");
        }
    }
}