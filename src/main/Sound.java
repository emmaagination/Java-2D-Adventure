package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {

        soundURL[0] = getClass().getClassLoader().getResource("sound/TemplePath.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sound/Retro PickUp Coin 07.wav");
        soundURL[2] = getClass().getClassLoader().getResource("sound/Retro PowerUP StereoUP 05.wav");
        soundURL[3] = getClass().getClassLoader().getResource("sound/Retro PickUp 10.wav");
        soundURL[4] = getClass().getClassLoader().getResource("sound/Retro Success Melody 04 - electric piano 2.wav");

    }

    public void setFile(int i) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by ## decibels.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}
