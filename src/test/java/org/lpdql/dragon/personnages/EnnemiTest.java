package org.lpdql.dragon.personnages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.interfaces.StoryElement;
import org.lpdql.dragon.scenario.Story;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;

import static org.junit.Assert.*;

public class EnnemiTest {

    private Ennemi anEnnemi;
    private Personnage testCharacter;

    @Before
    public void setUp() throws Exception {
        anEnnemi = new Ennemi("test", new Point(100, 100), 32, 32);
        testCharacter = new Personnage("test_man", new Point(100, 100), Taille.BASIC_SIZE, 100, 0f);
    }

    @After
    public void tearDown() throws Exception {
        anEnnemi = null;
        testCharacter = null;
    }

    @Test
    public void testCollisionPersonnagePossible() {
        anEnnemi.addCollision(testCharacter);
        assertTrue(anEnnemi.isCollisionPersonnage(anEnnemi.getX(), anEnnemi.getY()));
    }

    @Test
    public void testCollisionPersonnageImpossible() {
        testCharacter.setPosition(0, 0);
        anEnnemi.addCollision(testCharacter);
        assertFalse(anEnnemi.isCollisionPersonnage(anEnnemi.getX(), anEnnemi.getY()));
    }

    @Test
    public void testStoryElement() {
        anEnnemi.setStoryElement(Story.TESTSTORYELEMENT);
        assertTrue(anEnnemi.containStoryElement());
    }



}