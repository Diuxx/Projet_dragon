package org.lpdql.dragon.objets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.system.Point;

import static org.junit.Assert.*;

public class HealTest {

    private Heal heal;
    private Hero hero;

    @Before
    public void setUp() throws Exception {
        this.hero = new Hero("test");
        this.heal = new Heal(new Point(0, 0));
    }

    @After
    public void tearDown() throws Exception {
        this.heal = null;
        this.hero = null;
    }

    @Test
    public void isDisponibleTest() {
        assertTrue(this.heal.isDisponible());
    }

    @Test
    public void setDisponibleTest() {
        this.heal.setDisponible(false);
        assertFalse(this.heal.isDisponible());
    }

    @Test
    public void interactionPossibleTest() {
        this.hero.setPointDeVieActuel(0f);
        assertEquals(0, this.hero.getPointDeVieActuel(), 0.0001f);

        this.heal.interaction(this.hero);
        assertFalse(this.heal.isDisponible());
        assertEquals(this.hero.getPointDeVie(), this.hero.getPointDeVieActuel(), 0.0001f);
    }
}