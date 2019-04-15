package org.lpdql.dragon.bigBataille;

import org.lpdql.dragon.singleton.InterStateComm;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Bataille extends BasicGameState {

    public static final int ID = 22;

    private EnnemiBataille ennemiBataille;
    private HeroBataille heroBataille;

    private Image background;

    private boolean heroTurn = true;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.background = new Image("data/bataille/Forest_background.png");
        this.ennemiBataille = null;
        this.heroBataille = null;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {

        ennemiBataille = new EnnemiBataille(InterStateComm.getUnEnnemi(), container);
        heroBataille = new HeroBataille(InterStateComm.getLeHero(), container);

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        this.ennemiBataille = null;
        this.heroBataille = null;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());

        ennemiBataille.draw(graphics, gameContainer);
        heroBataille.draw(graphics, gameContainer);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_A == key) {
            System.out.println("<Bataille> attaque -> ");
            this.heroBataille.attaque(ennemiBataille);
        }
    }


}
