package com.codecool.dungeoncrawl.logic.actors;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Sounds {
    private static Clip bgMusic;
    private static Clip eventMusic ;

    public static void playSound(String fileName) {
        try {
            URL file = Sounds.class.getResource("/" + fileName + ".wav");
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip.open(ais);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.0f);
            clip.loop(0);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex)  {
            System.out.println("Valami nem stimmel a hanggal! :S");
        }
    }

    public static void playBGsound(String fileName) {
        try {
            stopBGsound();
            URL file = Sounds.class.getResource("/" + fileName + ".wav");
            bgMusic = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            bgMusic.open(ais);
            FloatControl gainControl =
                    (FloatControl) bgMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.0f); // change volume by x decibels (max. +6dB to increase, no limit on min)
            bgMusic.loop(-1);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex)  {
            System.out.println("Valami nem stimmel a hanggal! :S");
        }
    }

    public static void stopBGsound() {
        if (bgMusic != null && bgMusic.isOpen() && bgMusic.isRunning()) {
            bgMusic.stop();
        }
    }

    public static void playEVSound(String fileName) {
        try {
            stopBGsound();
            URL file = Sounds.class.getResource("/" + fileName + ".wav");
            eventMusic = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            eventMusic.open(ais);
            FloatControl gainControl =
                    (FloatControl) eventMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.0f); // change volume by x decibels (max. +6dB to increase, no limit on min)
            eventMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex)  {
            System.out.println("Valami nem stimmel a hanggal! :S");
        }
    }

    public static void stopEVsound() {
        if (eventMusic != null && eventMusic.isOpen() && eventMusic.isRunning()) {
            eventMusic.stop();
        }
    }
}