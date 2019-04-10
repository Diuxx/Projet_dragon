package org.lpdql.dragon.ecrans;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import org.lpdql.dragon.singleton.InterStateComm;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EcranLogo extends BasicGameState {
	public static final int ID = 23;
	private StateBasedGame stageGame;
	private Image background;


	@Override
	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
		this.background = new Image("data/bataille/youlose.png");
	}

	@Override
	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		
	}

	@Override
	public int getID() {
		return this.ID;
	}

}
