package org.lpdql.dragon.sound;


import org.lpdql.dragon.sound.error.SoundError;

import java.util.ArrayList;
import java.util.List;

/**
 * class MyJSound
 *
 * @author: Diuxx
 */
public class MyJSound<T> {

    private List<MyMusic> sounds;

    private float volumeMusic;
    private float volumeEffect;

    private boolean muted;

    /**
     * Class constructor
     */
    public MyJSound() {
        sounds = new ArrayList<>();
        this.muted = false;
        this.volumeMusic = 1f;
        this.volumeEffect = 1f;
    }

    public void addSound(T sound) {
        if(sound instanceof JSound)
            this.sounds.add((JSound) sound);
        else if(sound instanceof JMusic)
            this.sounds.add((JMusic) sound);
        else
            throw new SoundError("unknown type of music");
        // everything is working
    }

    public MyMusic getZik(String name)
    {
        for(MyMusic t : sounds) {
            if(t.getTitle().equals(name)) {
                return ((t instanceof JSound) ? (JSound)t : (JMusic)t);
            }
        }
        throw new SoundError("music not found");
    }

    public void loopZik(String name) {

        if(this.getZik(name) instanceof JSound) {
            // stop(name);
            ((JSound)this.getZik(name)).getMusic().loop(1f, this.volumeEffect);
        }

        if(this.getZik(name) instanceof JMusic)
            ((JMusic)this.getZik(name)).getMusic().loop(1f, this.volumeMusic);

    }

    public void playZik(String name) {

        if(this.getZik(name) instanceof JSound)
            ((JSound)this.getZik(name)).getMusic().play(1f, this.volumeEffect);

        if(this.getZik(name) instanceof JMusic)
            ((JMusic)this.getZik(name)).getMusic().play(1f, this.volumeMusic);

    }

    public void stopAll() {
        for(MyMusic t : sounds) {
            if(t instanceof JSound)
                ((JSound) t).getMusic().stop();

            if(t instanceof JMusic)
                ((JMusic) t).getMusic().stop();
        }
    }

    public void stop(String name) {
        if(this.getZik(name) instanceof JSound)
            ((JSound)this.getZik(name)).getMusic().stop();

        if(this.getZik(name) instanceof JMusic)
            ((JMusic)this.getZik(name)).getMusic().stop();
    }

    public boolean playing(String name) {

        return ((this.getZik(name) instanceof JSound) ? ((JSound)this.getZik(name)).getMusic().playing() : ((JMusic)this.getZik(name)).getMusic().playing());
    }


    public float getVolumeMusic() {
        return this.volumeMusic;
    }

    public void setVolumeMusic(float volume) {
        this.volumeMusic = (volume / 100);
    }

    public float setVolumeEffect() {
        return this.volumeEffect;
    }

    public void setVolumeEffect(float volume) {
        this.volumeEffect = (volume / 100);
    }
}
