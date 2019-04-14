package org.lpdql.dragon.sound;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 *
 */
class Title {
    private String title;
    private Music zik;
    private boolean muted;

    public Title(String title, String zikPath) throws SlickException {
        this.title = title;
        this.zik = new Music(zikPath);
        muted = false;
    }

    public String getTitle() {
        return title;
    }

    public Music getZik() {
        return zik;
    }
}
