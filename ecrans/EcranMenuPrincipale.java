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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * class EcranMenuPrincipale
 *
 * @author: eZ
 */
public class EcranMenuPrincipale extends BasicGameState {

	public static final int ID = 12;
	private StateBasedGame stageGame;
	private Image backgroundImage;
	private Font font1;
	private Font font2;
	private TrueTypeFont trueTypeFont1;
	private TrueTypeFont trueTypeFont2;
	private FontRenderContext tTypeFont;
	private String text1;
	private String text2;
	private String text3;
	private int text1X;
	private int text1Y;
	private int text2X;
	private int text2Y;
	private int text3X;
	private int text3Y;
	private int textwidth1;
	private int textheight1;
	private int textwidth2;
	private int textheight2;
	private int textwidth3;
	private int textheight3;

	private Color color1 = Color.white;
	private Color color2 = Color.white;
	private Color color3 = Color.white;

	@Override
	public int getID() {
		return this.ID;
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		this.stageGame = stateBasedGame;
		font1 = new Font("Matura MT Script Capitals", Font.PLAIN, 35);
		font2 = new Font("Tribal InterStateComm", Font.BOLD, 55);
		AffineTransform affinetransform = new AffineTransform();
		trueTypeFont1 = new TrueTypeFont(font1, true);
		trueTypeFont2 = new TrueTypeFont(font2, true);
		tTypeFont = new FontRenderContext(affinetransform, true, true);
		backgroundImage = new Image("Data/Menu_Principale.png");
		text1 = "Nouveau jeu";
		text2 = "Charger";
		text3 = "Sortie";
		text1X = (int) (main.Main.gX - (main.Main.gX * 0.25));
		text1Y = (int) (main.Main.gY - (main.Main.gY * 0.65));
		text2X = text1X;
		text2Y = text1Y + 90;
		text3X = text2X;
		text3Y = text2Y + 90;
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		textwidth1 = (int) (font1.getStringBounds(text1, tTypeFont).getWidth());
		textheight1 = (int) (font1.getStringBounds(text1, tTypeFont).getHeight());
		textwidth2 = (int) (font1.getStringBounds(text2, tTypeFont).getWidth());
		textheight2 = (int) (font1.getStringBounds(text2, tTypeFont).getHeight());
		textwidth3 = (int) (font1.getStringBounds(text3, tTypeFont).getWidth());
		textheight3 = (int) (font1.getStringBounds(text3, tTypeFont).getHeight());
		backgroundImage.draw(0, 0);
		graphics.setFont(trueTypeFont1);
		graphics.setColor(color1);
		graphics.drawString(this.text1, text1X, text1Y);
		graphics.setColor(color2);
		graphics.drawString(this.text2, text2X, text2Y);
		graphics.setColor(color3);
		graphics.drawString(this.text3, text3X, text3Y);
		graphics.setFont(trueTypeFont2);
		graphics.setColor(Color.orange);
		graphics.drawString("The InterStateComm King",
				(int) (main.Main.gX - (main.Main.gX * 0.515)
						- (int) (font2.getStringBounds("The InterStateComm King ", tTypeFont).getWidth() * 0.5)),
				(int) (main.Main.gY - (main.Main.gY * 0.98)));
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		float posX = Mouse.getX();
		float posY = Mouse.getY();
		if ((posX > text1X && posX < text1X + textwidth1)
				&& (posY > (600 - text1Y - textheight1) && posY < (600 - text1Y))) {
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				stageGame.enterState(EcranMenuChoisirUnNiveau.ID);
			}
			color1 = Color.red;
		} else {
			color1 = Color.white;
		}

		if ((posX > text2X && posX < text2X + textwidth2)
				&& (posY > (600 - text2Y - textheight2) && posY < (600 - text2Y))) {
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				// Charger un jeu ///////////////////////
			}
			color2 = Color.red;
		} else {
			color2 = Color.white;
		}

		if ((posX > text3X && posX < text3X + textwidth3)
				&& (posY > (600 - text3Y - textheight3) && posY < (600 - text3Y))) {
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				System.exit(0);
			}
			color3 = Color.red;
		} else {
			color3 = Color.white;
		}

	}
}
