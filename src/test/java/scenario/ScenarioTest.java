package scenario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.scenario.Scenario;
import org.lpdql.dragon.system.Direction;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.io.File;
import static org.junit.Assert.*;
import static org.lpdql.dragon.App.addToJavaLibraryPath;
import static org.lpdql.dragon.MyFunction._integration;

import org.lpdql.dragon.MyFunction;

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
    public void testtest() throws InterruptedException {
        final String[] test = {""};
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test[0] = "test";
            }
        });
        t.start();
        t.join();
        assertEquals("test", test[0]);
    }


    @Test
    public void findEnnemisWithOneEnnemi() throws InterruptedException, SlickException {

        MyFunction myFunction = new MyFunction() {
            @Override
            public String init(GameContainer gameContainer) throws SlickException {

                org.lpdql.dragon.monde.Ressources.charger();
                Scenario unScenario = new Scenario();
                unScenario.findEnnemis(new TiledMap("data/tests/testMap.tmx"));

                return String.valueOf(unScenario.getLesEnnemis().size());
            }
        };

        String[] result = _integration(myFunction);
        assertEquals("1", result[0]);
    }
}