package org.lpdql.dragon.bigBataille;

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

    private Image BoutonA;
    private Image BoutonF;
    private Image BoutonD;
    private Image BoutonBack;


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
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {

        sounds.playZik("battle");
        ennemiBataille = new EnnemiBataille(InterStateComm.getUnEnnemi(), container);
        heroBataille = new HeroBataille(InterStateComm.getLeHero(), container);
        this.effetsCombats = new ArrayList<>();
        this.levelExperience = new LevelExperience();

        this.BoutonBack =  new Image("data/bataille/BoutonFond.png");
        this.BoutonA =  new Image("data/bataille/Bouton1.png");
        this.BoutonF =  new Image("data/bataille/BoutonFuir.png");
        this.BoutonD =  new Image("data/bataille/BoutonDefendre.png");
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

        ennemiBataille.draw(graphics, gameContainer);
        heroBataille.draw(graphics, gameContainer);

        for(int i=0; i<this.effetsCombats.size(); i++)
            if(this.effetsCombats.get(i).getAnimation().isStopped())
                this.effetsCombats.remove(i);

        for(Effet e : this.effetsCombats) {
            e.afficher(graphics);
        }

        this.BoutonBack.draw(-170,426);
        this.BoutonA.draw(10,450);
        this.BoutonD.draw(10,500);
        this.BoutonF.draw(10,550);
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
            effetsCombats.add(this.swingEffet(ennemiBataille));
            Ressources.sounds.playZik("attaque");
            this.heroBataille.attaque(ennemiBataille);
            this.heroAttaque = true;
        }
        if (Input.KEY_F == key) {
            InterStateComm.getUnEnnemi().setFriendly(true, 20000); // --
            InterStateComm.getUnEnnemi().seCalme();
            InterStateComm.getUnEnnemi().setPointDeVieActuel(InterStateComm.getUnEnnemi().getPointDeVie());
            InterStateComm.enleverUnEnnemi();

            this.stateBasedGame.enterState(EcranJeu.ID);
        }
        if (Input.KEY_D == key) {
            // def
            Ressources.sounds.playZik("attaque");

        }
    }

    boolean ennemiAttaque = false;

    private void checkEnnemiTurn() {
        if(!heroTurn && !ennemiAttaque) {
            // here ennemi attaque
            Ressources.sounds.playZik("attaque");
            System.out.println("<Bataille> Ennemi turn");
            this.ennemiBataille.attaque(this.heroBataille);
            this.ennemiAttaque = true;
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

    private Effet swingEffet(EnnemiBataille ennemi) {
        Effet e = new Effet("swing", ennemi.getPosition(), new Taille(59, 68));
        e.loadAnimation(Ressources.spriteSheet_swordHit, 0, 2, 0, new int[] {200, 200});
        e.getAnimation().stopAt(1);
        return e;
        // for movible effet extend a new class MovibleEffet with Effet, and add depart position & endPosition..
    }

}
