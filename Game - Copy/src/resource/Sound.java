package resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    public URL[] soundURL = new URL[32];
    public long musicProgress;

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/LOZ_Main_Theme.wav");
        soundURL[1] = getClass().getResource("/sound/LOZ_Link_Hurt.wav");
        soundURL[2] = getClass().getResource("/sound/LOZ_MagicalRod.wav");
        soundURL[3] = getClass().getResource("/sound/LOZ_Sword_Slash.wav");
        soundURL[4] = getClass().getResource("/sound/LOZ_Enemy_Hit.wav");
        soundURL[5] = getClass().getResource("/sound/LOZ_Enemy_Die.wav");
        soundURL[6] = getClass().getResource("/sound/LOZ_Link_Die.wav");
        soundURL[7] = getClass().getResource("/sound/LOZ_Fanfare.wav");
        soundURL[8] = getClass().getResource("/sound/LOZ_Fanfare2.wav");
        soundURL[9] = getClass().getResource("/sound/LOZ_Arrow_Boomerang.wav");
    }
    public void setFile(int _index, float _volume) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[_index]);
            clip = AudioSystem.getClip();

            clip.open(ais);

            FloatControl audioController = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            audioController.setValue(_volume);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();

    }
    public void pauseMusic(int _delay) {
        musicProgress = clip.getMicrosecondPosition();
        clip.stop();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        clip.start();
                        clip.setMicrosecondPosition(musicProgress);
                    }
                },
                _delay
        );


    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}
