package org.lpdql.dragon.scenario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.MyFunction;
import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.scenario.Scenario;
import org.lpdql.dragon.system.Direction;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.io.File;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.lpdql.dragon.App.addToJavaLibraryPath;
import static org.lpdql.dragon.MyFunction._integration;

public class ScenarioTest {

    private Scenario unScenario;

    @Before
    public void setUp() throws Exception {

        addToJavaLibraryPath(new File("lib/natives-windows"));
        unScenario = new Scenario();
    }

    @After
    public void tearDown() throws Exception {

        unScenario = null;
    }

    @Test
    public void getDirectionFromStringTestCorrectValues() {
        assertEquals(Direction.HORIZONTAL, unScenario.getDirectionFromString("h"));
        assertEquals(Direction.VERTICAL, unScenario.getDirectionFromString("v"));
        assertEquals(Direction.RANDOM, unScenario.getDirectionFromString("r"));
        assertEquals(Direction.IMMOBILE, unScenario.getDirectionFromString("i"));
    }

    @Test
    public void getDirectionFromStringTestDefault() {
        assertEquals(Direction.IMMOBILE, unScenario.getDirectionFromString("default"));
    }

    @Test
    public void findEnnemisWithOneEnnemi() {

        MyFunction myFunction = new MyFunction() {
            @Override
            public String init(GameContainer gameContainer) throws SlickException {

                org.lpdql.dragon.monde.Ressources.charger();
                Scenario unScenario = new Scenario();
                unScenario.findEnnemis(new TiledMap("data/tests/testMap.tmx"));

                return String.valueOf(unScenario.getLesEnnemis().size());
            }
        };

        try {

            String[] result = _integration(myFunction);
            assertEquals("1", result[0]);

        } catch(SlickException e) {
            fail("SlickException.class should not be raised");
        }
    }
}