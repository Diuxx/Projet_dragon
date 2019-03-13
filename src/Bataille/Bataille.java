package Bataille;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import jeu.Ennemi;
import jeu.Hero;

public class Bataille extends BasicGameState{

	public static final int ID = 3;
	
	private Image background;
	private BatailleEnnemi batailleEnnemi;
	private BatailleJoeur batailleJoueur;
	private Ennemi ennemi;
	public static final int ATTAQUER = 0;
	
	
	@Override
	public void init(GameContainer fenetreDeBataille, StateBasedGame game) throws SlickException {
		this.background = new Image("data/bataille/background.jpg");
		this.batailleEnnemi = new BatailleEnnemi();
		this.batailleJoueur = new BatailleJoeur();
		batailleEnnemi.init();
		batailleJoueur.init();
		
		InputProvider provider = new InputProvider(fenetreDeBataille.getInput());
		provider.bindCommand(new KeyControl(Input.KEY_A), BatailleCommande.ATTAQUER);
		provider.bindCommand(new KeyControl(Input.KEY_F), BatailleCommande.FUIRE);
		provider.addListener(new BatailleControlle(batailleJoueur, batailleEnnemi, game));
	}
	
	@Override
	public void render(GameContainer fenetreDeJeu, StateBasedGame game, Graphics graph) throws SlickException {
		background.draw(0, 0, fenetreDeJeu.getWidth(), fenetreDeJeu.getHeight());
		batailleEnnemi.render(fenetreDeJeu, graph);
		batailleJoueur.render(fenetreDeJeu, graph);
	}

	@Override
	public void update(GameContainer fenetreDeJeu, StateBasedGame game, int delta) throws SlickException {
		this.batailleJoueur.update(delta);
		this.batailleEnnemi.update(delta);
	}
	
	@Override
	public int getID() {
		return ID;
	}
}