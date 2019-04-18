package org.lpdql.dragon.sound;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.sound.error.SoundError;
import org.newdawn.slick.SlickException;

import java.io.File;

import static org.junit.Assert.*;
import static org.lpdql.dragon.App.addToJavaLibraryPath;

public class MyJSoundTest {

    private MyJSound mesSounds;

    @Before
    public void setUp() throws Exception {

        mesSounds = new MyJSound();
        addToJavaLibraryPath(new File("lib/natives-windows"));
    }

    @After
    public void tearDown() throws Exception {

        mesSounds = null;
    }

    @Test
    public void addSoundTestJMusic() throws SlickException {
        mesSounds.addSound(new JMusic("test", "/data/sound/ambiant.ogg"));
        assertTrue(mesSounds.getZik("test") instanceof JMusic);
    }

    @Test
    public void addSoundTestJSound() throws SlickException {
        mesSounds.addSound(new JSound("test", "/data/sound/ambiant.ogg"));
        assertTrue(mesSounds.getZik("test") instanceof JSound);
    }

    @Test
    public void addSoundTestError() throws SlickException {
        try {
            mesSounds.addSound(new String("test"));
            fail("SoundError should be raised");
        } catch(SoundError e) {
            assertEquals("unknown type of music", e.getMessage());
        }
    }

    @Test
    public void getZikTestError() throws SlickException {
        try {
            mesSounds.addSound(new JSound("test", "/data/sound/ambiant.ogg"));
            mesSounds.getZik("bellezik");
            fail("SoundError should be raised");
        } catch(SoundError e) {
            assertEquals("music not found", e.getMessage());
        }
    }

    @Test
    public void loopZikTest() throws SlickException {
        mesSounds.addSound(new JSound("test", "/data/sound/ambiant.ogg"));
        mesSounds.loopZik("test");
    }

    @Test
    public void loopZikErrorTest() throws SlickException {
        mesSounds.addSound(new JSound("test", "/data/sound/ambiant.ogg"));
        try {
            mesSounds.loopZik("boom");
            fail("SoundError should be raised");
        } catch (SoundError e) {
            assertEquals("music not found", e.getMessage());
        }
    }

    @Test
    public void playZikTest() throws SlickException {
        mesSounds.addSound(new JSound("test", "/data/sound/ambiant.ogg"));
        mesSounds.playZik("test");
    }

    @Test
    public void playZikErrorTest() throws SlickException {
        mesSounds.addSound(new JSound("test", "/data/sound/ambiant.ogg"));
        try {
            mesSounds.playZik("boom");
            fail("SoundError should be raised");
        } catch (SoundError e) {
            assertEquals("music not found", e.getMessage());
        }
    }
}