package org.lpdql.dragon.personnages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.jeu.Message;

import static org.junit.Assert.*;

public class PersonnageNonJoueurTest {

    private PersonnageNonJoueur nonPlayablecharacter;

    @Before
    public void setUp() throws Exception {

        nonPlayablecharacter = new PersonnageNonJoueur("test", 0, 0, 32, 32);
    }

    @After
    public void tearDown() throws Exception {

        nonPlayablecharacter = null;
    }

    @Test
    public void testGetDiablogueWithNoDialogueSet() {
        try {
            Message message = nonPlayablecharacter.getDialogue();
            assertEquals("", message.getText().get(0));
        } catch(NullPointerException e) {
            fail("NullPointerException not should be raised..");
        } catch(IndexOutOfBoundsException f) {
            fail("IndexOutOfBoundsException not should be raised..");
        }
    }


}