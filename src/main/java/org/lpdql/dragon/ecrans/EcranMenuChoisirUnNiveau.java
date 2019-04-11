package org.lpdql.dragon.ecrans;

import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Difficulty;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * class EcranMenuChoisirUnNiveau
 *
 * @author: eZ
 */
public class EcranMenuChoisirUnNiveau extends BasicGameState {
// --
	public static final int ID = 4;
	public static String niveau;
	private StateBasedGame stateBasedGame;
	private Image backgroundImage;
	private Image flecheDeRetour;
	private Font font1;
	private Font font2;
	private TrueTypeFont trueTypeFont1;
	private TrueTypeFont trueTypeFont2;
	private FontRenderContext tTypeFont;
	private String text1;
	private String text2;
	private String text3;
	private String text4;
	private int text1X;
	private int text1Y;
	private int text2X;
	private int text2Y;
	private int text3X;
	private int text3Y;
	private int text4X;
	private int text4Y;
	private int textwidth2;
	private int textheight2;
	private int textwidth3;
	private int textheight3;
	private int textwidth4;
	private int textheight4;
	protected static int timer = 0;

	private Color color1 = Color.white;
	private Color color2 = Color.white;
	private Color color3 = Color.white;
	private Color color4 = Color.white;

	@Override
	public int getID() {
		return this.ID;
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		this.stateBasedGame = stateBasedGame;
		font1 = new Font("Matura MT Script Capitals", Font.PLAIN, 35);
		font2 = new Font("Tribal Dragon", Font.BOLD, 55);
		AffineTransform affinetransform = new AffineTransform();
		trueTypeFont1 = new TrueTypeFont(font1, true);
		trueTypeFont2 = new TrueTypeFont(font2, true);
		tTypeFont = new FontRenderContext(affinetransform, true, true);
		backgroundImage = new Image("Data/MenuChoisirUnNiveau.png");
		flecheDeRetour = new Image("Data/arrow.png");
		text1 = "Choisir Un Niveau";
		text2 = "Facile";
		text3 = "Difficile";
		text4 = "Tres difficile";
		text1X = (int) (InterStateComm.gX - (InterStateComm.gX * 0.98));
		text1Y = (int) (InterStateComm.gY - (InterStateComm.gY * 0.65));
		text2X = text1X + 100;
		text2Y = text1Y + 60;
		text3X = text2X - 20;
		text3Y = text2Y + 50;
		text4X = text3X - 30;
		text4Y = text3Y + 50;
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		textwidth2 = (int) (font1.getStringBounds(text2, tTypeFont).getWidth());
		textheight2 = (int) (font1.getStringBounds(text2, tTypeFont).getHeight());
		textwidth3 = (int) (font1.getStringBounds(text3, tTypeFont).getWidth());
		textheight3 = (int) (font1.getStringBounds(text3, tTypeFont).getHeight());
		textwidth4 = (int) (font1.getStringBounds(text4, tTypeFont).getWidth());
		textheight4 = (int) (font1.getStringBounds(text4, tTypeFont).getHeight());
		backgroundImage.draw(0, 0);
		flecheDeRetour.draw(0, 0);
		graphics.setFont(trueTypeFont1);
		graphics.setColor(color1);
		graphics.drawString(this.text1, text1X, text1Y);
		graphics.setColor(color2);
		graphics.drawString(this.text2, text2X, text2Y);
		graphics.setColor(color3);
		graphics.drawString(this.text3, text3X, text3Y);
		graphics.setColor(color4);
		graphics.drawString(this.text4, text4X, text4Y);
		graphics.setFont(trueTypeFont2);
		graphics.setColor(Color.yellow);
		graphics.drawString("The Dragon King",
				(int) (InterStateComm.gX - (InterStateComm.gX * 0.515)
						- (int) (font2.getStringBounds("The Dragon King ", tTypeFont).getWidth() * 0.5)),
				(int) (InterStateComm.gY - (InterStateComm.gY * 0.98)));
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		float posX = Mouse.getX();
		float posY = Mouse.getY();
		timer += delta;
		if ((posX > text2X && posX < text2X + textwidth2)
				&& (posY > (600 - text2Y - textheight2) && posY < (600 - text2Y))) {
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				InterStateComm.setNiveauDuJeu(Difficulty.FACILE);
				stateBasedGame.enterState(EcranMenuChoisirUnNom.ID);
			}
			color2 = Color.red;
		} else {
			color2 = Color.white;
		}

		if ((posX > text3X && posX < text3X + textwidth3)
				&& (posY > (600 - text3Y - textheight3) && posY < (600 - text3Y))) {
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				InterStateComm.setNiveauDuJeu(Difficulty.DIFFICILE);
				nextStateGame();
			}
			color3 = Color.red;
		} else {
			color3 = Color.white;
		}

		if ((posX > text4X && posX < text4X + textwidth4)
				&& (posY > (600 - text4Y - textheight4) && posY < (600 - text4Y))) {
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				InterStateComm.setNiveauDuJeu(Difficulty.TRES_DIFFICILE);
				nextStateGame();
			}
			color4 = Color.red;
		} else {
			color4 = Color.white;
		}

		if (timer > 500) {
			if ((posX > 0 && posX < flecheDeRetour.getWidth())
					&& (posY > (600 - flecheDeRetour.getHeight()) && posY < 600)) {
				if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					nextStateGame();
				}
			}
		}

	}

	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ENTER == key) {
			InterStateComm.setNiveauDuJeu(Difficulty.FACILE);
			nextStateGame();
		}
	}
	
	public void nextStateGame() {
		this.stateBasedGame.enterState(EcranMenuChoisirUnNom.ID);
	}
}
