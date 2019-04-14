package org.lpdql.dragon.sound;


import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 * class Sound
 *
 * @author: Diuxx
 */
public class JMusic implements MyMusic {

    private String title;
    private Music sound;

    private boolean playing;

    /**
     * Class constructor
     */
    public JMusic(String title, String path) throws SlickException {
        this.title = title;
        this.sound = new Music(path);
        this.playing = false;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Music getMusic() {
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