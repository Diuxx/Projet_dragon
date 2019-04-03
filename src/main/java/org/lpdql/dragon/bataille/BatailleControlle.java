package org.lpdql.dragon.bataille;

import org.lpdql.dragon.jeu.LevelExperience;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.singleton.InterStateComm;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 */
public class BatailleControlle implements InputProviderListener {

	// --
	private BatailleEnnemi ennemi;// = unE.getBataille();
	private BatailleJoueur joueur;
	private StateBasedGame game;
	private BatailleCommande mode = BatailleCommande.NONE;
	private Sound swing;
	private Sound victory;
	private LevelExperience levelExperience;

	public BatailleControlle(BatailleJoueur joueur, BatailleEnnemi ennemi, StateBasedGame game) {

		try {
			swing = new Sound("data/sound/swing.wav");
			victory = new Sound("data/sound/ff7victory.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		} finally {
			System.err.println("data/sound -> loaded");
		}
		this.joueur = joueur;
		this.ennemi = ennemi;
		this.game = game;
		initAnimationListeners();
		this.levelExperience = new LevelExperience();
	}

	public void controlPressed(Command commande) {
		if (this.mode == BatailleCommande.NONE) {
			this.mode = (BatailleCommande) commande;
			attaquer();
		}
	}

	private void attaquer() {
		attaqueJoeur();
	}

	private void attaqueJoeur() {
		this.joueur.attaquer( this.ennemi);
	}

	private void attaqueEnnemi() {
		this.ennemi.attaquer();
	}

	public void controlReleased(Command commande) {

	}

	/**
	 *
	 */
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
		AnimationListener ennemiAssignDamage = new AnimationListener() {
			@Override
			public void on() {
			ennemiAssignDamage();
			}
		};
		AnimationListener endEnnemiAttack = new AnimationListener() {
			@Override
			public void on() {
			endEnnemiAttack();
			}
		};
		joueur.addAnimationListener(playerAssignDamage,endPlayerAttack);
		ennemi.addAnimationListener(ennemiAssignDamage,endEnnemiAttack);
	}

	/**
	 *
	 */
	private void playerAssignDamage() {
		switch (mode) {
			case ATTAQUER:
			if(!InterStateComm.getLeHero().getMuted() && swing != null) {
				swing.play();
			}
			ennemi.setBarreVie(50);
			break;
			default:
		}
	}


	/**
	 *
	 */
	private void endPlayerAttack() {
		if(ennemi.getBarreVie() <= 0) {
			if(!InterStateComm.getLeHero().getMuted() && victory != null)
			{
				victory.play();
			}

			System.out.println(InterStateComm.getUnEnnemi().getNom() + " est mort !");
			InterStateComm.tuerUnEnnemi();

			System.out.println(InterStateComm.getUnEnnemi());

			game.enterState(GameOver.GameOver);

			// on ajoute l'experience de l'ennemi dans le joueur
			InterStateComm.getLeHero().setExperience(ennemi.getExperience() + InterStateComm.getLeHero().getExperience());
			
			// on check s'il y a level up
			levelExperience.checkUpLevelEtExperience(InterStateComm.getLeHero().getExperience(), InterStateComm.getLeHero());
			

			mode = BatailleCommande.NONE;
		} else {
			switch (mode) {
				case ATTAQUER:
					ennemi.attaquer();
					break;
				case FUIRE:
					System.out.println("> fuire ");
					InterStateComm.getUnEnnemi().setFriendly(true, 20000); // --
                    InterStateComm.getUnEnnemi().seCalme();
                    // on enlève l'ennemi de la bataille
                    InterStateComm.enleverUnEnnemi();

					game.enterState(GameOver.GameOver);
					ennemi.regenVie();
					mode = BatailleCommande.NONE;
					break;
				default:
			}
		}
	}

	/**
	 *
	 */
	private void ennemiAssignDamage() {
		joueur.setBarreVie(10);
		if(!InterStateComm.getLeHero().getMuted() && swing != null) {
			swing.play();
		}
	}

	/**
	 *
	 */
	private void endEnnemiAttack() {
		if (joueur.getBarreVie() <= 0) {
			game.enterState(GameOver.GameOver);
		}
		mode = BatailleCommande.NONE;
	}
}