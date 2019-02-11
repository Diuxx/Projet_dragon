package sys;


import Bataille.Bataille;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * class StateGame
 *
 * @author: Diuxx
 */
public class StateGame extends StateBasedGame {

    /**
     * Class constructor
     */
    public StateGame() {
        super("Projet Dragon");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new EcranMenu());
        addState(new EcranJeu());
        addState(new Bataille());
    }


}
