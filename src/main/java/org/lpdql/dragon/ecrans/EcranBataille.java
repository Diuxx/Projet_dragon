package org.lpdql.dragon.ecrans;

import org.lpdql.dragon.bataille.BatailleCommande;
import org.lpdql.dragon.bataille.BatailleControlle;
import org.lpdql.dragon.bataille.BatailleEnnemi;
import org.lpdql.dragon.bataille.BatailleJoueur;
import org.newdawn.slick.*;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EcranBataille extends BasicGameState{

	public static final int ID = 9;
	
	private Image background;
	private BatailleEnnemi batailleEnnemi;
	private BatailleJoueur batailleJoueur;
	public static final int ATTAQUER = 0;
	
	
	@Override
	public void init(GameContainer fenetreDeBataille, StateBasedGame game) throws SlickException {
		this.background = new Image("data/bataille/Forest_background.png");
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