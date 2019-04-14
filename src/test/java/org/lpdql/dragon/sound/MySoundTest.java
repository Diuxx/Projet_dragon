package org.lpdql.dragon.sound;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.system.Direction;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class MySoundTest {

    public MySound sounds;

    @Before
    public void setUp() throws Exception {
        sounds = new MySound();
    }

    @After
    public void tearDown() throws Exception {
        sounds = null;
    }

    @Test
    public void getZikErrorTest() {
        try {
            sounds.getZik("test");
            fail("a NoSuchElementException Error should be raised");
        } catch(NoSuchElementException e) {
            assertEquals("test is a unreachable Music", e.getMessage());
        }
    }

    @Test
    public void getNoiseErrorTest() {
        try {
            sounds.getNoise("test");
            fail("a NoSuchElementException Error should be raised");
        } catch(NoSuchElementException e) {
            assertEquals("test is a unreachable Sound", e.getMessage());
        }
    }

    @Test
    public void isTiteExistTest() {
        assertFalse(sounds.isTitleExist("test"));
    }

    @Test
    public void loopSoundErrorTest() {
        try {
            sounds.loopSound("test");
            fail("NoSuchElementException Error should be raised");
        } catch(NoSuchElementException e) {
            assertEquals("test is a unreachable Sound", e.getMessage());
        }
    }

}
