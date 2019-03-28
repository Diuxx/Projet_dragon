package scenario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
    public void findEnnemisWithOneEnnemi() throws InterruptedException {
        final String[] test = {""};
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AppGameContainer app = new AppGameContainer(new BasicGame("test") {
                        @Override
                        public void init(GameContainer gameContainer) throws SlickException {

                            org.lpdql.dragon.monde.Ressources.charger();
                            Scenario threadScenario = new Scenario();
                            threadScenario.findEnnemis(new TiledMap("data/tests/testMap.tmx"));

                            test[0] += threadScenario.getLesEnnemis().size();

                            threadScenario = null;
                            gameContainer.exit();
                        }

                        @Override
                        public void update(GameContainer gameContainer, int i) throws SlickException {

                        }

                        @Override
                        public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

                        }
                        @Override
                        public boolean closeRequested() {
                            // do whatever logic you want in here and return true if the game should close.
                            return true;
                        }
                    }, 10, 10, false);
                    app.setForceExit(false);
                    app.start();
                    app.destroy();
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.join();
        assertEquals("1", test[0]);
    }
}