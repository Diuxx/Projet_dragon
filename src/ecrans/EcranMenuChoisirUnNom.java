package ecrans;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import singleton.InterStateComm;
import sys.EcranJeu;

/**
 * class EcranMenuChoisirUnNom
 *
 * @author: eZ
 */
public class EcranMenuChoisirUnNom extends BasicGameState {

	public static final int ID = 13;
	private StateBasedGame stageGame;
	Image backgroundImage;
	Image flecheDeRetour;
	TextField inputNom;
	Font font1;
	Font font2;
	TrueTypeFont trueTypeFont1;
	TrueTypeFont trueTypeFont2;
	FontRenderContext tTypeFont;
	public static String nom;
	private String text1;
	private String text2;
	private int inputNomX;
	private int inputNomY;
	private int text1X;
	private int text1Y;
	private int text2X;
	private int text2Y;
	private int textwidth;
	private int textheight;
	private Color color1 = Color.white;
	private Color color2 = Color.white;

	@Override
	public int getID() {
		return this.ID;
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		this.stageGame = stateBasedGame;
		font1 = new Font("Matura MT Script Capitals", Font.PLAIN, 35);
		font2 = new Font("Tribal Dragon", Font.BOLD, 55);
		AffineTransform affinetransform = new AffineTransform();
		trueTypeFont1 = new TrueTypeFont(font1, true);
		trueTypeFont2 = new TrueTypeFont(font2, true);
		tTypeFont = new FontRenderContext(affinetransform, true, true);
		backgroundImage = new Image("Data/MenuChoisirUnNom.png");
		flecheDeRetour = new Image("Data/arrow.png");
		text1 = "Entrez votre nom";
		text2 = "Lancer";
		inputNomX = (int) (InterStateComm.gX - (InterStateComm.gX * 0.25));
		inputNomY = (int) (InterStateComm.gY - (InterStateComm.gY * 0.55));
		text1X = inputNomX + 15;
		text1Y = inputNomY - 50;
		text2X = inputNomX + 80;
		text2Y = inputNomY + 60;
		inputNom = new TextField(gameContainer, gameContainer.getDefaultFont(), inputNomX, inputNomY, 290, 25);
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		textwidth = (int) (font1.getStringBounds(text2, tTypeFont).getWidth());
		textheight = (int) (font1.getStringBounds(text2, tTypeFont).getHeight());
		backgroundImage.draw(0, 0);
		flecheDeRetour.draw(0, 0);
		graphics.setFont(trueTypeFont1);
		graphics.setColor(color1);
		graphics.drawString(this.text1, text1X, text1Y);
		inputNom.render(gameContainer, graphics);
		graphics.setColor(color2);
		graphics.drawString(this.text2, text2X, text2Y);
		graphics.setFont(trueTypeFont2);
		graphics.setColor(Color.white);
		graphics.drawString("The Dragon King",
				(int) (InterStateComm.gX - (InterStateComm.gX * 0.515)
						- (int) (font2.getStringBounds("The Dragon King ", tTypeFont).getWidth() * 0.5)),
				(int) (InterStateComm.gY - (InterStateComm.gY * 0.98)));
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		float posX = Mouse.getX();
		float posY = Mouse.getY();
		if ((posX > text2X && posX < text2X + textwidth)
				&& (posY > (600 - text2Y - textheight) && posY < (600 - text2Y))) {
			if (!inputNom.getText().equals("")) {
				if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					InterStateComm.getLeHero().setNom(inputNom.getText());
					stageGame.enterState(EcranJeu.ID);
				}
				color2 = Color.red;
			} else {
				color2 = Color.white;
			}
		}

		if ((posX > 0 && posX < flecheDeRetour.getWidth())
				&& (posY > (600 - flecheDeRetour.getHeight()) && posY < 600)) {
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				EcranMenuChoisirUnNiveau.timer = 0;
				stageGame.enterState(EcranMenuChoisirUnNiveau.ID);
			}
		}
	}
}
