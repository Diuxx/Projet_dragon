package Bataille;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.state.StateBasedGame;
import sys.EcranJeu;

public class BatailleControlle implements InputProviderListener {
	private BatailleEnnemi ennemi ;
	private BatailleJoeur joueur;
	private StateBasedGame game;
	private BatailleCommande mode = BatailleCommande.NONE;



	
	public BatailleControlle(BatailleJoeur joueur, BatailleEnnemi ennemi, StateBasedGame game) {
		this.joueur = joueur;
		this.ennemi = ennemi;
		this.game = game;
		initAnimationListeners();
	}

	@Override
	public void controlPressed(Command commande) {
		if (this.mode == BatailleCommande.NONE)
			this.mode = (BatailleCommande) commande;
			attaquer();
	}

	private void attaquer() {
		attaqueJoeur();
		attaqueEnnemi();
	}
	
	private void attaqueJoeur() {
		joueur.attaquer(ennemi);

	}
	private void attaqueEnnemi() {
		ennemi.attaquer(joueur);

	}
	
	@Override
	public void controlReleased(Command commande) {

	}
	private void initAnimationListeners() {
		  AnimationListener playerAssignDamage = new AnimationListener() {
		    @Override
		    public void on() {
		    	playerAssignDamage();
		    }
		  };
		  AnimationListener endPlayerAttack = new AnimationListener() {
			    @Override
			    public void on() {
			      endPlayerAttack();
			    }
			  };
			  this.joueur.addAnimationListener(playerAssignDamage,endPlayerAttack);
			}
	  private void playerAssignDamage() {ennemi.setBarreVie(10); }
	  private void endPlayerAttack() { 	
		  if(ennemi.getBarreVie()<= 0) {
			  //game.enterState(YouWin.YouWin);
			  game.enterState(EcranJeu.ID);
		  }

		  attaqueEnnemi();
		  joueur.setBarreVie(10);
		  mode = BatailleCommande.NONE;

	  }
	
}