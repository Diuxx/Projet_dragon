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
    public void testTextSize() {
        this.message.add("test#test");
        assertEquals(2, this.message.getTextSize());
    }

    @Test
    public void testTextSizeWithMultipleAdd() {
        this.message.add("test#test");
        assertEquals(2, this.message.getTextSize());
        this.message.add("test#test");
        assertEquals(4, this.message.getTextSize());
    }

    @Test
    public void testTextSizeWithoutText() {
        assertEquals(0, this.message.getTextSize());
    }

    @Test
    public void testPosition() {
        this.message.add("test#test");
        assertEquals(0, this.message.getPosisition());
    }

    @Test
    public void testNextPosition() {
        this.message.add("test#test");
        assertEquals(0, this.message.getPosisition());

        assertTrue(this.message.next());
        assertEquals(1, this.message.getPosisition());
    }

    @Test
    public void testNoMoreMessage() {
        this.message.add("test#test");
        assertTrue(this.message.next());
        assertTrue(this.message.next());

        assertTrue(this.message.containMessage());
    }

}