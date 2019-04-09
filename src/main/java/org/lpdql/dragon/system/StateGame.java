package org.lpdql.dragon.system;

import org.lpdql.dragon.bataille.Bataille;
import org.lpdql.dragon.bataille.GameOver;
import org.lpdql.dragon.ecrans.*;
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
		/**
		 * add stats here for team description..
		 */

		addState(new EcranMenuPrincipale());
		addState(new EcranMenuChoisirUnNiveau());
		addState(new EcranMenuChoisirUnNom());
		addState(new EcranJeu());
		addState(new Bataille());
		addState(new GameOver());

	}
}
