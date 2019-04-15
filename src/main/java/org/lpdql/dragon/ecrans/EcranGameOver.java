package org.lpdql.dragon.ecrans;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import org.lpdql.dragon.bataille.BatailleEnnemi;
import org.lpdql.dragon.bataille.BatailleJoueur;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.sauvegarde.Save;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Point;
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
import static org.lpdql.dragon.monde.Ressources.sounds;
=======
/**
 * class StateGame
 *
 * @author: Ez
 */


public class EcranGameOver extends BasicGameState {
	public static final int ID = 10;
	private StateBasedGame stageGame;
	private Image background;
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
	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
		this.background = new Image("data/bataille/youlose.png");
		this.stageGame = stateBasedGame;
		font1 = new Font("Matura MT Script Capitals", Font.PLAIN, 35);
		font2 = new Font("Tribal Dragon", Font.BOLD, 55);
		AffineTransform affinetransform = new AffineTransform();
		trueTypeFont1 = new TrueTypeFont(font1, true);
		trueTypeFont2 = new TrueTypeFont(font2, true);
		tTypeFont = new FontRenderContext(affinetransform, true, true);
		text1 = "Nouveau jeu";
		text2 = "Charger";
		text3 = "Sortie";
		text1X = (int) (InterStateComm.gX - (InterStateComm.gX * 0.23));
		text1Y = (int) (InterStateComm.gY - (InterStateComm.gY * 0.73));
		text2X = text1X;
		text2Y = text1Y + 90;
		text3X = text2X;
		text3Y = text2Y + 90;
	}

	private Hero h = null;

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		/*if(!sounds.playing("menu"))
			sounds.loopZik("menu");*/

		h = Save.detectSavedData().getSavedHero();
	}

	@Override
	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0, 0, game.getWidth(), game.getHeight());
		textwidth1 = (int) (font1.getStringBounds(text1, tTypeFont).getWidth());
		textheight1 = (int) (font1.getStringBounds(text1, tTypeFont).getHeight());
		textwidth2 = (int) (font1.getStringBounds(text2, tTypeFont).getWidth());
		textheight2 = (int) (font1.getStringBounds(text2, tTypeFont).getHeight());
		textwidth3 = (int) (font1.getStringBounds(text3, tTypeFont).getWidth());
		textheight3 = (int) (font1.getStringBounds(text3, tTypeFont).getHeight());
		graphics.setFont(trueTypeFont1);
		graphics.setColor(color1);
		graphics.drawString(this.text1, text1X, text1Y);

		if(this.h == null) {
			graphics.setColor(Color.red);
		} else {
			graphics.setColor(color2);
		}
		graphics.drawString(this.text2, text2X, text2Y);


		graphics.setColor(color3);
		graphics.drawString(this.text3, text3X, text3Y);
		graphics.setFont(trueTypeFont2);
		graphics.setColor(Color.orange);
		graphics.drawString("The Dragon King",
				(int) (InterStateComm.gX - (InterStateComm.gX * 0.515)
						- (int) (font2.getStringBounds("The Dragon King ", tTypeFont).getWidth() * 0.45)),
				(int) (InterStateComm.gY - (InterStateComm.gY * 0.98)));

	}

	@Override
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

		if(this.h != null) {
			if ((posX > text2X && posX < text2X + textwidth2)
					&& (posY > (600 - text2Y - textheight2) && posY < (600 - text2Y))) {
				if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					// charger
					if (Save.detectSavedData().getSavedHero() != null) {
						EcranJeu.init = false;
						// InterStateComm.setLeHero(new Hero("LPDQL", new Point(0, 0)));
						InterStateComm.enleverUnEnnemi();
						stateBasedGame.enterState(EcranJeu.ID);
					}
				}
				color2 = Color.red;
			} else {
				color2 = Color.white;
			}
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

	@Override
	public int getID() {
		return EcranGameOver.ID;
	}

}
