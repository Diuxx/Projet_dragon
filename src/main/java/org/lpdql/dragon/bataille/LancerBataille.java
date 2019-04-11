package org.lpdql.dragon.bataille;

import org.lpdql.dragon.ecrans.Bataille;
import org.lpdql.dragon.ecrans.EcranGameOver;
import org.lpdql.dragon.ecrans.YouWin;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

class LancerBataille extends StateBasedGame {

	  public LancerBataille(String name) {
		super(name);
	}

	public static void main(String[] args) throws SlickException {
	    new AppGameContainer(new LancerBataille("Hero vs Ennemi1"), 800, 600, false).start();
	  }
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new Bataille());
		addState(new EcranGameOver());
		addState(new YouWin());
	}
}
