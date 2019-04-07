package org.lpdql.dragon.scenario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.scenario.Accomplish;
import org.lpdql.dragon.scenario.Scenario;
import org.lpdql.dragon.scenario.errors.ScenarioError;

import static org.junit.Assert.*;

public class AccomplishTest {

    /**
     *
     */
    private Accomplish progression;

    @Before
    public void setUp() throws Exception {
        progression = new Accomplish();
    }

    @After
    public void tearDown() throws Exception {
        progression = null;
    }

    @Test
    public void getCurrentArtTest() {
        assertEquals("Epee", this.progression.getCurrentArt());
    }

    @Test
    public void getCurrentArtTestWithCompletedArt() {
        try {
            // fin de l'art "epée".
            this.progression.setEndArt("Epee");
        } catch(ScenarioError e) {
            fail("ScenarioError.class should not be raised !");
        }
        assertEquals("Bouclier", this.progression.getCurrentArt());
    }
}