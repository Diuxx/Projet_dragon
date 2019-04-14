package org.lpdql.dragon.sound;

/**
 * class MyMusic
 *
 * @author: Diuxx
 */
public interface MyMusic<T> {
    public String getTitle();
    public T getMusic();
    public boolean isPlaying();
    public void setPlaying(boolean b);
}
