package org.lpdql.dragon.ecrans;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EcranLogoUT3 extends BasicGameState {
	public static final int ID = 23;
	private Image logo;
	private float logoX;
	private float logoY;
	private long current = System.currentTimeMillis();
	private float alpha = 0.01f;
	private boolean f = false;

	@Override
	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
		logo = new Image("Data/logos/UT3_Logo.png");
		logo.setAlpha(0.1f);
		logoX = (game.getWidth() / 2) - (logo.getWidth() / 2);
		logoY = (game.getHeight() / 2) - (logo.getHeight() / 2);
	}

	@Override
	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		logo.draw(logoX, logoY);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

		if (System.currentTimeMillis() - current > 10 && !f) {
			logo.setAlpha(logo.getAlpha() + this.alpha);
			current = System.currentTimeMillis();
		}

		if (logo.getAlpha() > 1f && !f) {
			f = true;
			this.alpha = -0.01f;
		}

		if (logo.getAlpha() <= 0.0F) {
			stateBasedGame.enterState(EcranLogoIUT.ID);
		}

		if (System.currentTimeMillis() - current > 1000 && f) {
			f = false;
			logo.setAlpha(logo.getAlpha() - 0.01f);
			current = System.currentTimeMillis();
		}

	}

	@Override
	public int getID() {
		return this.ID;
	}

}
