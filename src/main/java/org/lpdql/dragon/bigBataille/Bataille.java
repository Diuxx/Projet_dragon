package org.lpdql.dragon.bigBataille;

import org.lpdql.dragon.bataille.BatailleEnnemi;
import org.lpdql.dragon.ecrans.EcranGameOver;
import org.lpdql.dragon.ecrans.EcranJeu;
import org.lpdql.dragon.effet.Effet;
import org.lpdql.dragon.jeu.LevelExperience;
import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

import static org.lpdql.dragon.monde.Ressources.sounds;

public class Bataille extends BasicGameState {

    public static final int ID = 22;
    private StateBasedGame stateBasedGame;

    private EnnemiBataille ennemiBataille;
    private HeroBataille heroBataille;

    private Image background;

    private List<Effet> effetsCombats;

    private boolean heroTurn = true;
    private boolean heroAttaque = false;

    private LevelExperience levelExperience;

    // Attaque Animation
    private List<AttaqueAnimation> attaqueAnimations;
    GameContainer gameContainer;
	Graphics graphics;
	
	// Bouns atk
	private int defance;
	private int atk;
    
    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
        this.background = new Image("data/bataille/battleground.png");
        this.ennemiBataille = null;
        this.heroBataille = null;
        this.effetsCombats = null;
        attaqueAnimations =null;
        this.gameContainer = null;
        this.graphics = null;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame game) throws SlickException {
    	attaqueAnimations = new ArrayList<>();
        sounds.playZik("battle");
        ennemiBataille = new EnnemiBataille(InterStateComm.getUnEnnemi(), gameContainer);
        heroBataille = new HeroBataille(InterStateComm.getLeHero(), gameContainer);
        this.effetsCombats = new ArrayList<>();
        this.levelExperience = new LevelExperience();
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {

        sounds.stopAll();
        this.ennemiBataille = null;
        this.heroBataille = null;
        this.levelExperience = null;
        this.effetsCombats = null;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
		this.gameContainer = gameContainer;
		this.graphics = graphics;
        ennemiBataille.draw(graphics, gameContainer);
        heroBataille.draw(graphics, gameContainer);

        for(int i=0; i<this.effetsCombats.size(); i++)
            if(this.effetsCombats.get(i).getAnimation().isStopped())
                this.effetsCombats.remove(i);

        for(Effet e : this.effetsCombats) {
            e.afficher(graphics);
        }
        
    	for (AttaqueAnimation attaqueAnimation : attaqueAnimations) {
			attaqueAnimation.drawAtq(graphics);
		}

		for (int i = 0; i < attaqueAnimations.size(); i++) {
			if (attaqueAnimations.get(i).isEnd())
				attaqueAnimations.remove(i);
		}
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        heroBataille.update(this.ennemiBataille, this.effetsCombats);
        ennemiBataille.update(this.heroBataille);

        if(heroAttaque) {
            if(this.heroBataille.isAnnimationEnd())
            {
                // fin attaque hero
                this.finAttaqueHero();
                this.heroAttaque = false;
                this.heroTurn = false;
            }
        }
        // now it's ennemi turn..
        this.checkEnnemiTurn();
    }

    @Override
    public void keyReleased(int key, char c) {
        if(!(!heroAttaque && this.heroTurn && this.heroBataille.isAnnimationEnd()))
            return;

        if (Input.KEY_A == key) {
            heroStartAttaque();
            System.out.println("Hero ATK power      ----> " + (int) heroBataille.getATK() + " atk + " + this.atk);
			System.out.println("ennemi restant vie  ----> " + (int) ennemiBataille.getEnnemi().getPointDeVieActuel());
			System.out.println();
			this.atk = 0;
        }
        
        if (Input.KEY_D == key) {
        	ennemiStartAttaque();
        	this.addAttaqueAnimationSurHero("DEFENCE");

			// bloqueur 50% ~ 75% de la ennemi attaque
			int r = (int) (Math.random() * (25)) + 50;
			System.out.println("blocker : " + r + "%");
			this.defance = (int) (heroBataille.getATK() * r / 100);
			System.out.println("defance blocke : " + this.defance);

			// augmenter la prochine joueur attaque 25% ~ 75%
			int s = (int) (Math.random() * (50)) + 25;
			System.out.println("Atk : " + s + "%");
			this.atk = (int) (heroBataille.getATK() * s / 100);
			System.out.println("atk augmenter " + this.atk);
			System.out.println();
        }
        
        if (Input.KEY_F == key) {
            InterStateComm.getUnEnnemi().setFriendly(true, 20000); // --
            InterStateComm.getUnEnnemi().seCalme();
            InterStateComm.getUnEnnemi().setPointDeVieActuel(InterStateComm.getUnEnnemi().getPointDeVie());
            InterStateComm.enleverUnEnnemi();

            this.stateBasedGame.enterState(EcranJeu.ID);
        }
        if (Input.KEY_B == key) {
            // def
        }
    }

	private void heroStartAttaque() {
		effetsCombats.add(this.swingEffet(ennemiBataille));
		Ressources.sounds.playZik("attaque");
		this.heroBataille.attaque(ennemiBataille);
		this.addAttaqueAnimationSurEnnemi(
				"- " + (int) (Math.min(ennemiBataille.getEnnemi().getPointDeVieActuel(), (int) (heroBataille.getATK() + this.atk))));
		this.heroAttaque = true;
		this.atk = 0;
		
	}
	
	private void ennemiStartAttaque() {
		Ressources.sounds.playZik("attaque");
		System.out.println("<Bataille> Ennemi turn");
		this.ennemiBataille.attaque(this.heroBataille);
		this.addAttaqueAnimationSurHero(
				"- " + (int) (Math.min(heroBataille.getHero().getPointDeVieActuel(), (int) (ennemiBataille.getATK() - this.defance))));
		this.ennemiAttaque = true;
		this.defance = 0;
	}

    boolean ennemiAttaque = false;

    private void checkEnnemiTurn() {
        if(!heroTurn && !ennemiAttaque) {
            // here ennemi attaque
            ennemiStartAttaque();
        }
        if(ennemiAttaque) {
            if(this.ennemiBataille.isAnnimationEnd())
            {
                // fin attaque ennemi
                this.finAttaqueEnnemi();
                this.ennemiAttaque = false;
                this.heroTurn = true;
            }
        }
    }

    // --
    private void finAttaqueEnnemi() {
        if (heroBataille.getHero().getPointDeVieActuel() <= 0) {
            InterStateComm.enleverUnEnnemi();
            this.stateBasedGame.enterState(EcranGameOver.ID);
        }
    }

    // --
    private void finAttaqueHero() {
        boolean levelUP = false;
        if(ennemiBataille.getEnnemi().getPointDeVieActuel() <= 0) {
            EcranJeu.victory = true;
            System.out.println(InterStateComm.getUnEnnemi().getNom() + " est mort !");
            InterStateComm.tuerUnEnnemi();


            InterStateComm.getLeHero().setExperience(ennemiBataille.getEnnemi().getExperience() + heroBataille.getHero().getExperience());
            levelUP = levelExperience.checkUpLevelEtExperience(InterStateComm.getLeHero().getExperience(), InterStateComm.getLeHero());
            if (levelUP) {
                InterStateComm.getLeHero().rafraichirLePouvoirATK();
                InterStateComm.getLeHero().setHeroStatistques(InterStateComm.getLeHero().getLevel());
            }
            this.stateBasedGame.enterState(EcranJeu.ID);
        }
    }

    public void addAttaqueAnimationSurEnnemi(String text) {
    	float y = (this.gameContainer.getHeight() / 2 - ennemiBataille.getEnnemi().getEnnemiImages().getHeight() / 2 - 50);
    	if (this.attaqueAnimations.size() > 0) {
    		y += this.attaqueAnimations.size() * 4 + 10;
    	}
		this.attaqueAnimations.add(new AttaqueAnimation(text, 500, this.gameContainer.getWidth() * 3 / 4 ,y));
	}

    public void addAttaqueAnimationSurHero(String text) {
    	float y = (this.gameContainer.getHeight() / 2 - heroBataille.getJoueurImage().getHeight() / 2 - 20);
    	if (this.attaqueAnimations.size() > 0) {
    		y += this.attaqueAnimations.size() * 4 + 10;
    	}
		this.attaqueAnimations.add(new AttaqueAnimation(text, 500, this.gameContainer.getWidth() * 1 / 4 - 60 + (95/2) + 40,
				y));
	}
    
    private Effet swingEffet(EnnemiBataille ennemi) {
        Effet e = new Effet("swing", ennemi.getPosition(), new Taille(59, 68));
        e.loadAnimation(Ressources.spriteSheet_swordHit, 0, 2, 0, new int[] {200, 200});
        e.getAnimation().stopAt(1);
        return e;
        // for movible effet extend a new class MovibleEffet with Effet, and add depart position & endPosition..
    }

}
