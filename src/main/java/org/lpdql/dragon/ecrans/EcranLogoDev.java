package org.lpdql.dragon.ecrans;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import org.lpdql.dragon.singleton.InterStateComm;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * class StateGame
 *
 * @author: Ez
 */

public class EcranLogoDev extends BasicGameState {
	public static final int ID = 6;
	private StateBasedGame stateBasedGame;
	private Font font1;
	private Font font2;
	private TrueTypeFont trueTypeFont1;
	private TrueTypeFont trueTypeFont2;
	private FontRenderContext tTypeFont;
	private String text1;
	private String text2;
	private String text3;
	private String text4;
	private String text5;
	private String text6;
	private String text7;
	private String text8;
	private int text1X;
	private int text1Y;
	private int text2X;
	private int text2Y;
	private int text3X;
	private int text3Y;
	private int text4X;
	private int text4Y;
	private int text5X;
	private int text5Y;
	private int text6X;
	private int text6Y;
	private int text7X;
	private int text7Y;
	private int text8X;
	private int text8Y;
	private long current = System.currentTimeMillis();
	private int moveY = 600;
	private boolean f = false;

	@Override
	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
		this.stateBasedGame = stateBasedGame;
		font1 = new Font("Times New Roman", Font.PLAIN, 19);
		font2 = new Font("Tribal Dragon", Font.BOLD, 55);
		AffineTransform affinetransform = new AffineTransform();
		trueTypeFont1 = new TrueTypeFont(font1, true);
		trueTypeFont2 = new TrueTypeFont(font2, true);
		tTypeFont = new FontRenderContext(affinetransform, true, true);
		text1 = "SCRUM MASTER";
		text2 = "Zohal Akabr";
		text3 = "PRODUCT OWNER";
		text4 = "Mathieu CASTRO";
		text5 = "** DEVELOPPEURS **";
		text6 = "Ez-Aldin AL-HAMAD";
		text8 = "Ambidine ASSANI";
		text7 = "Nicolas MARMOT";

	}

	@Override
	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		text1X = (int) ((InterStateComm.gX / 2) - (font1.getStringBounds(text1, tTypeFont).getWidth() / 2));
		text1Y = moveY;
		text2X = (int) ((InterStateComm.gX / 2) - (font1.getStringBounds(text2, tTypeFont).getWidth() / 2));
		text2Y = text1Y + 50;
		text3X = (int) ((InterStateComm.gX / 2) - (font1.getStringBounds(text3, tTypeFont).getWidth() / 2));
		text3Y = text2Y + 50;
		text4X = (int) ((InterStateComm.gX / 2) - (font1.getStringBounds(text4, tTypeFont).getWidth() / 2));
		text4Y = text3Y + 50;
		text5X = (int) ((InterStateComm.gX / 2) - (font1.getStringBounds(text5, tTypeFont).getWidth() / 2));
		text5Y = text4Y + 50;
		text6X = (int) ((InterStateComm.gX / 2) - (font1.getStringBounds(text6, tTypeFont).getWidth() / 2));
		text6Y = text5Y + 50;
		text7X = (int) ((InterStateComm.gX / 2) - (font1.getStringBounds(text7, tTypeFont).getWidth() / 2));
		text7Y = text6Y + 50;
		text8X = (int) ((InterStateComm.gX / 2) - (font1.getStringBounds(text8, tTypeFont).getWidth() / 2));
		text8Y = text7Y + 50;
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, InterStateComm.gX, InterStateComm.gY);
		graphics.setColor(Color.white);
		graphics.setFont(trueTypeFont2);
		graphics.drawString("The Dragon King",
				(int) ((InterStateComm.gX / 2) - (font2.getStringBounds("The Dragon King ", tTypeFont).getWidth() / 2)),
				20);
		graphics.setFont(trueTypeFont1);
		graphics.setColor(Color.red);
		graphics.drawString(this.text1, text1X, text1Y);
		graphics.setColor(Color.white);
		graphics.drawString(this.text2, text2X, text2Y);
		graphics.setColor(Color.red);
		graphics.drawString(this.text3, text3X, text3Y);
		graphics.setColor(Color.white);
		graphics.drawString(this.text4, text4X, text4Y);
		graphics.setColor(Color.red);
		graphics.drawString(this.text5, text5X, text5Y);
		graphics.setColor(Color.white);
		graphics.drawString(this.text6, text6X, text6Y);
		graphics.drawString(this.text7, text7X, text7Y);
		graphics.drawString(this.text8, text8X, text8Y);

	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

		if (System.currentTimeMillis() - current > 10 && !f) {
			this.moveY -= 1;
			current = System.currentTimeMillis();
		}

		if (this.moveY <= 120 && !f) {
			f = true;
		}

		if (System.currentTimeMillis() - current > 2000 && f) {
			f = false;
			nextStateGame();
			current = System.currentTimeMillis();
		}

	}

	@Override
	public int getID() {
		return EcranLogoDev.ID;
	}

	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ENTER == key || Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			nextStateGame();
		}
	}

	public void nextStateGame() {
		this.stateBasedGame.enterState(EcranLogoJeu.ID);
	}

}
