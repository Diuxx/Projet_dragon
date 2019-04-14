package org.lpdql.dragon.sound;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Noise {

    private String title;
    private Sound noise;

    public Noise(String titre, String path) throws SlickException {

        this.title = title;
        this.noise = new Sound(path);
    }

    public String getTitle() {
        return this.title;
    }

    public Sound getNoise() {
        return this.noise;
    }
}
