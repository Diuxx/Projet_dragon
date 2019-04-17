package org.lpdql.dragon.ecrans;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * class StateGame
 *
 * @author: Ez
 */

public class EcranLogoJeu extends BasicGameState {
	public static final int ID = 17;
	private StateBasedGame stateBasedGame;
	private Image logo;
	private Image present;
	private float logoX;
	private float logoY;
	private long current = System.currentTimeMillis();
	private float alphaLogo = 0.0f;
	private float alphaPresent = 0.01f;

	@Override
	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
		this.stateBasedGame = stateBasedGame;
		logo = new Image("Data/logos/dragonLogo.png");
		present = new Image("Data/logos/Present2.png");
		logo.setAlpha(0.01f);
		present.setAlpha(0.01f);
		logoX = (game.getWidth() / 2) - (logo.getWidth() / 2);
		logoY = (game.getHeight() / 2) - (logo.getHeight() / 2);
	}

	@Override
	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		logo.draw(logoX, logoY + 60);
		present.draw((game.getWidth() / 2) - (present.getWidth() / 2), logoY - 50);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

		if (System.currentTimeMillis() - current > 20) {
			present.setAlpha(present.getAlpha() + this.alphaPresent);
			logo.setAlpha(logo.getAlpha() + this.alphaLogo);
			current = System.currentTimeMillis();
		}

		if (present.getAlpha() >= 1f) {
			this.alphaPresent = -0.01f;
			this.alphaLogo = 0.01f;
		}

		if (present.getAlpha() <= 0.0F) {
			this.alphaLogo = -0.01f;
		}

		if (logo.getAlpha() <= 0.0F) {
			nextStateGame();
		}

		if (System.currentTimeMillis() - current > 1000) {
			present.setAlpha(present.getAlpha() - 0.01f);
			current = System.currentTimeMillis();
		}

	}

	@Override
	public int getID() {
		return EcranLogoJeu.ID;
	}

	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ENTER == key || Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			nextStateGame();
		}
	}

	public void nextStateGame() {
		this.stateBasedGame.enterState(EcranMenuPrincipale.ID);
	}

}
