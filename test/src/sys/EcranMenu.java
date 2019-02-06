package sys;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * class EcranMenu
 *
 * @author: Diuxx
 */
public class EcranMenu extends BasicGameState {

    public static final int ID = 1;
    private StateBasedGame stageGame;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stageGame = stateBasedGame;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawString("Appuyer sur une touche", 300, 300);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void keyReleased(int key, char c) {
        stageGame.enterState(EcranJeu.ID);
    }
}
