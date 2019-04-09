package org.lpdql.dragon.scenario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.system.Direction;

import java.io.File;

import static org.junit.Assert.*;
import static org.lpdql.dragon.App.addToJavaLibraryPath;

public class ScenarioTest {

    private Scenario unScenario;

    @Before
    public void setUp() throws Exception {

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
}