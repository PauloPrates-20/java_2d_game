package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundController {
    Clip clip;
    URL soundURL[] = new URL[30];

    public final static int THEME = 0;
    public final static int COIN = 1;
    public final static int POWERUP = 2;
    public final static int UNLOCK = 3;
    public final static int FANFARE = 4;

    public SoundController() {
        soundURL[THEME] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[COIN] = getClass().getResource("/sound/coin.wav");
        soundURL[POWERUP] = getClass().getResource("/sound/powerup.wav");
        soundURL[UNLOCK] = getClass().getResource("/sound/unlock.wav");
        soundURL[FANFARE] = getClass().getResource("/sound/fanfare.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
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
