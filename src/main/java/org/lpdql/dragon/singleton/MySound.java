package org.lpdql.dragon.singleton;


import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * class MySound
 *
 * @author: Diuxx
 */
public class MySound {

    private List<Title> myZiks;

    /**
     * Class constructor
     */
    public MySound() {
        myZiks = new ArrayList<>();
    }

    /**
     *
     * @param path
     */
    public void addZik(String name, String path) throws SlickException {
        myZiks.add(new Title(name, path));
    }

    /**
     *
     * @param name
     * @return
     */
    public Music getTitle(String name)
    {
        for(Title t : myZiks) {
            if(t.getTitle().equals(name))
                return t.getZik();
        }
        return null; // throw err here !
    }

    /**
     *
     * @param name
     */
    public void playTitle(String name) {
        this.getTitle(name).loop();
    }

    /**
     *
     */
    class Title {
        String title;
        Music zik;
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
}
