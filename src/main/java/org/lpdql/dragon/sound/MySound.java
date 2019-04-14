package org.lpdql.dragon.sound;


import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * class MySound
 *
 * @author: Diuxx
 */
public class MySound {

    private List<Title> myZiks;
    private List<Noise> myNoises;

    /**
     * Class constructor
     */
    public MySound() {
        myZiks = new ArrayList<>();
        myNoises = new ArrayList<>();
    }

    /**
     *
     * @param path
     */
    public void addSound(String name, String path, int type)
            throws SlickException {
        if(type == 0) {
            myZiks.add(new Title(name, path));
        } else if(type == 1) {
            myNoises.add(new Noise(name, path));
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public Music getZik(String name)
    {
        for(Title t : myZiks) {
            if(t.getTitle().equals(name))
                return t.getZik();
        }
        throw new NoSuchElementException(name + " is a unreachable Music");
    }

    /**
     *
     * @param title
     * @return
     */
    public boolean isTitleExist(String title) {
        boolean exist = false;
        try {
            this.getNoise(title);
            exist = true;
        } catch(NoSuchElementException e) {
            try {
                this.getZik(title);
                exist = true;
            } catch (NoSuchElementException f) {
                // --
            }
        }
        return exist;
    }

    /**
     *
     * @param name
     * @return
     */
    public Sound getNoise(String name)
    {
        for(Noise n : myNoises) {
            if(n.getTitle().equals(name))
                return n.getNoise();
        }
        throw new NoSuchElementException(name + " is a unreachable Sound");
    }

    /**
     *
     * @param name
     */
    public void loopSound(String name) {
        if(!this.isTitleExist(name))
            throw new NoSuchElementException(name + " is a unreachable Sound");

        try {
            this.getNoise(name).loop();
        } catch(NoSuchElementException e) {
            try {
                this.getZik(name).loop();
            } catch(NoSuchElementException m) {
                throw new NoSuchElementException("this sound does not exist");
            }
        }
    }

    /**
     *
     * @param name
     */
    public void playSound(String name) {
        if(!this.isTitleExist(name))
            throw new NoSuchElementException(name + " is a unreachable Sound");

        try {
            this.getNoise(name).play();
        } catch(NoSuchElementException e) {
            try {
                this.getZik(name).play();
            } catch(NoSuchElementException m) {
                throw new NoSuchElementException("this sound does not exist");
            }
        }
    }

    /**
     *
     */
    public void stopAll() {

    }
}
