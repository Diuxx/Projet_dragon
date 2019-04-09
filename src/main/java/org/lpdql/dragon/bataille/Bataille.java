package org.lpdql.dragon.bataille;

import org.newdawn.slick.*;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Bataille extends BasicGameState{

	public static final int ID = 3;
	
	private Image background;
	private BatailleEnnemi batailleEnnemi;
	private BatailleJoueur batailleJoueur;
	public static final int ATTAQUER = 0;
	
	
	@Override
	public void init(GameContainer fenetreDeBataille, StateBasedGame game) throws SlickException {
		this.background = new Image("data/bataille/background.jpg");
		this.batailleEnnemi = new BatailleEnnemi();
		this.batailleJoueur = new BatailleJoueur();
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