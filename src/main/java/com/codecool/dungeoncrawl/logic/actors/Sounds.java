package com.codecool.dungeoncrawl.logic.actors;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sounds {
    public static void playSound(String fileName) {
        try {
            File file = new File("src/main/resources/" + fileName + ".wav");
            Clip clip = AudioSystem.getClip();
            // getAudioInputStream() also accepts a File or InputStream
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip.open(ais);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.0f); // change volume by x decibels (max. +6dB to increase, no limit on min)
            clip.loop(0);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex)  {
            System.out.println("Valami nem stimmel a hanggal! :S");
        }
    }
}