package org.lpdql.dragon.jeu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {

    private Message message;

    @Before
    public void setUp() throws Exception {

        this.message = new Message();
    }

    @After
    public void tearDown() throws Exception {

        this.message = null;
    }

    @Test
    public void testGetNextMessage() {
        assertTrue(true);
    }
}