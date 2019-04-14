package org.lpdql.dragon.sound;


import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * class Sound
 *
 * @author: Diuxx
 */
public class JSound implements MyMusic {

    private String title;
    private Sound sound;

    private boolean playing;

    /**
     * Class constructor
     */
    public JSound(String title, String path) throws SlickException {
        this.title = title;
        this.sound = new Sound(path);
        this.playing = false;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Sound getMusic() {
        return this.sound;
    }

    @Override
    public boolean isPlaying() {
        return this.playing;
    }

    @Override
    public void setPlaying(boolean b) {
        this.playing = b;
    }
}
