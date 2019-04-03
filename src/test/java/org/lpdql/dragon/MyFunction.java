package org.lpdql.dragon;

import org.newdawn.slick.*;

/**
 *
 */
public class MyFunction {
    public String init(GameContainer gameContainer) throws SlickException { return null; }
    public String update(GameContainer gameContainer) { return null; }
    public String render(GameContainer gameContainer) { return null; }

    public static String[] _integration(MyFunction function) throws SlickException {

        final String[] test = {"", "", ""};

        AppGameContainer app = new AppGameContainer(new BasicGame("test") {
            @Override
            public void init(GameContainer gameContainer) throws SlickException {
                test[0] = function.init(gameContainer);
                gameContainer.exit();
            }

            @Override
            public void update(GameContainer gameContainer, int i) throws SlickException {
                test[1] = function.update(gameContainer);
                gameContainer.exit();
            }

            @Override
            public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
                test[2] = function.render(gameContainer);
                gameContainer.exit();
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

        return test;
    }
}