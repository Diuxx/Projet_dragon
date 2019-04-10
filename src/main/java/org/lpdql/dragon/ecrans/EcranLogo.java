package org.lpdql.dragon.ecrans;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EcranLogo extends BasicGameState {
	public static final int ID = 23;
	private Image logo_1;
	float logo_1_X;
	float logo_1_Y;

	@Override
	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
		logo_1 = new Image("Data/logos/UT3_Logo.png");
		logo_1.setAlpha(0.1f);
		logo_1_X = (game.getWidth() / 2) - (logo_1.getWidth() / 2);
		logo_1_Y = (game.getHeight() / 2) - (logo_1.getHeight() / 2);
	}

	@Override
	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		logo_1.draw(logo_1_X, logo_1_Y);
	}

	long current = System.currentTimeMillis();
	float alpha = 0.1f;
	boolean f = false;
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		
		if (System.currentTimeMillis() - current > 100 && !f) {
			logo_1.setAlpha(logo_1.getAlpha() + this.alpha);
			current = System.currentTimeMillis();
		}

		if (logo_1.getAlpha() > 1f && !f) {
			f = true;
			this.alpha = -0.1f;
		}
		
		if (logo_1.getAlpha() <= 0.0F) {
			System.exit(0);
		}

		if (System.currentTimeMillis() - current > 1000 && f) {
			f = false;
			logo_1.setAlpha(logo_1.getAlpha() - 0.01f);
			current = System.currentTimeMillis();
		}			

	}

	@Override
	public int getID() {
		return this.ID;
	}

}
