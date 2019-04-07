package org.lpdql.dragon.personnages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;

import static org.junit.Assert.*;

public class EnnemiTest {

    private Ennemi anEnnemi;

    @Before
    public void setUp() throws Exception {
        anEnnemi = new Ennemi("test", new Point(100, 100), 32, 32);
    }

    @After
    public void tearDown() throws Exception {
        anEnnemi = null;
    }

    @Test
    public void testCollisionPersonnage() {

        anEnnemi.addCollision(new Personnage("test", new Point(90, 50), Taille.BASIC_SIZE, 100, 0f));

        assertTrue(anEnnemi.is);


    }
}