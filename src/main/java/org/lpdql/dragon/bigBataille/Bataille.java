package org.lpdql.dragon.bigBataille;

import org.lpdql.dragon.ecrans.EcranGameOver;
import org.lpdql.dragon.ecrans.EcranJeu;
import org.lpdql.dragon.effet.Effet;
import org.lpdql.dragon.jeu.LevelExperience;
import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Point;
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

    boolean ennemiAttaque = false;
    boolean fuite = false;

    // Attaque Animation
    private List<AttaqueAnimation> attaqueAnimations;
    GameContainer gameContainer;
    Graphics graphics;

    private Image BoutonA;
    private Image BoutonF;
    private Image BoutonD;
    private Image BoutonBack;

    @Override
    public int getID() {
        return Bataille.ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
        this.background = new Image("data/bataille/battleground.png");
        this.ennemiBataille = null;
        this.heroBataille = null;
        this.effetsCombats = null;
        attaqueAnimations = null;
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

        this.BoutonBack =  new Image("data/bataille/BoutonFond.png");
        this.BoutonA =  new Image("data/bataille/Bouton1.png");
        this.BoutonF =  new Image("data/bataille/BoutonFuir.png");
        this.BoutonD =  new Image("data/bataille/BoutonDefendre.png");
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        sounds.stopAll();
        if(fuite)
            Ressources.sounds.playZik("leave");
        this.ennemiBataille = null;
        this.heroBataille = null;
        this.levelExperience = null;
        this.effetsCombats = null;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        this.gameContainer = gameContainer;
        this.graphics = graphics;
        ennemiBataille.draw(graphics, gameContainer);
        heroBataille.draw(graphics, gameContainer);

        for (int i = 0; i < this.effetsCombats.size(); i++)
            if (this.effetsCombats.get(i).getAnimation().isStopped())
                this.effetsCombats.remove(i);

        for (Effet e : this.effetsCombats) {
            e.afficher(graphics);
        }

        for (AttaqueAnimation attaqueAnimation : attaqueAnimations) {
            attaqueAnimation.drawAtq(graphics);
        }

        for (int i = 0; i < attaqueAnimations.size(); i++) {
            if (attaqueAnimations.get(i).isEnd())
                attaqueAnimations.remove(i);
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

        if (heroAttaque) {
            if (this.heroBataille.isAnnimationEnd()) {
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
        if (!(!heroAttaque && this.heroTurn && this.heroBataille.isAnnimationEnd()))
            return;

        if (Input.KEY_A == key) {
            heroStartAttaque();
        }

        /*if (Input.KEY_D == key) {
            System.out.println();
            System.out.println("<Bataille> Hero choisier defance");
            bloquerProchinEnnemiAtk(); // bloqueur 5% ~ 15% de la prochine ennemi attaque
            augmenterProchineHeroAtk(); // augmenter la prochine hero attaque 25% ~ 75%
            ennemiStartAttaque();
        }*/

        if (Input.KEY_F == key) {
            InterStateComm.getUnEnnemi().setFriendly(true, 20000); // --
            InterStateComm.getUnEnnemi().seCalme();
            InterStateComm.getUnEnnemi().setPointDeVieActuel(InterStateComm.getUnEnnemi().getPointDeVie());
            InterStateComm.enleverUnEnnemi();

            this.fuite = true;
            this.stateBasedGame.enterState(EcranJeu.ID);
        }
        if (Input.KEY_D == key) {
            effetsCombats.add(this.heroBataille.shiealdEffet());// Ressources.sounds.playZik("attaque");
            Ressources.sounds.playZik("armure");

            this.heroBataille.defence(ennemiBataille);
            this.addAttaqueAnimationSurHero("Defance " + EnnemiBataille.DEFANCE + "%");

            this.heroAttaque = true;
        }
    }

    private void heroStartAttaque() {
        effetsCombats.add(this.heroBataille.swingEffet(ennemiBataille));
        this.heroBataille.attaque(ennemiBataille);
        this.addAttaqueAnimationSurEnnemi("- " + heroBataille.getATK());
        if (HeroBataille.ATK > 0) {
            this.addAttaqueAnimationSurEnnemi("Critical - " + HeroBataille.ATK);
            Ressources.sounds.playZik("critical2");
        } else {
            Ressources.sounds.playZik("sword");
        }
        this.heroAttaque = true;
    }

    private void ennemiStartAttaque() {
        Ressources.sounds.playZik("attaque");
        this.ennemiBataille.swingEffet(this.heroBataille);

        this.ennemiBataille.attaque(this.heroBataille);

        this.addAttaqueAnimationSurHero("- " + (ennemiBataille.getATK() - EnnemiBataille.DEFANCE));
        /*if (EnnemiBataille.DEFANCE > 0) {
            this.addAttaqueAnimationSurHero("Defance " + EnnemiBataille.DEFANCE + "%");
        }*/
        this.ennemiAttaque = true;
    }

    private void checkEnnemiTurn() {
        if (!heroTurn && !ennemiAttaque) {
            // here ennemi attaque
            ennemiStartAttaque();
        }
        if (ennemiAttaque) {
            if (this.ennemiBataille.isAnnimationEnd()) {
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
        if (ennemiBataille.getEnnemi().getPointDeVieActuel() <= 0) {
            EcranJeu.victory = true;
            System.out.println();
            System.err.println(InterStateComm.getUnEnnemi().getNom() + " est mort !");
            System.err.println("Hero win !");
            InterStateComm.tuerUnEnnemi();

            InterStateComm.getLeHero()
                    .setExperience(ennemiBataille.getEnnemi().getExperience() + heroBataille.getHero().getExperience());
            levelUP = levelExperience.checkUpLevelEtExperience(InterStateComm.getLeHero().getExperience(),
                    InterStateComm.getLeHero());
            if (levelUP) {
                InterStateComm.getLeHero().rafraichirLePouvoirATK();
                InterStateComm.getLeHero().setHeroStatistques(InterStateComm.getLeHero().getLevel());
            }
            this.stateBasedGame.enterState(EcranJeu.ID);
        }
    }

    public void addAttaqueAnimationSurEnnemi(String text) {
        float heightDeText = this.gameContainer.getHeight() / 2
                - ennemiBataille.getEnnemi().getEnnemiImages().getHeight() / 2 - 40;
        float widthPosition = this.gameContainer.getWidth() * 3 / 4;
        int tempsDeAfficher = 500;
        if (!this.attaqueAnimations.isEmpty()) {
            heightDeText += 15;
            widthPosition -= 29;
            tempsDeAfficher += 400;
        }
        this.attaqueAnimations.add(new AttaqueAnimation(text, tempsDeAfficher, widthPosition, heightDeText));
    }

    public void addAttaqueAnimationSurHero(String text) {
        float heightPostion = this.gameContainer.getHeight() / 2 - heroBataille.getJoueurImage().getHeight() / 2 - 15;
        float widthPosition = this.gameContainer.getWidth() * 1 / 4 - 60 + (95 / 2) + 40;
        int tempsDeAfficher = 500;
        if (!this.attaqueAnimations.isEmpty()) {
            heightPostion += 15;
            widthPosition -= 25;
            tempsDeAfficher += 400;
        }
        this.attaqueAnimations.add(new AttaqueAnimation(text, tempsDeAfficher, widthPosition, heightPostion));
    }

    private void bloquerProchinEnnemiAtk() {
        int r = (int) (Math.random() * (10)) + 5;
        System.out.print("Heros blocker " + r + "%");
        EnnemiBataille.DEFANCE = (int) (heroBataille.getATK() * r / 100);
        System.out.println(" (+" + EnnemiBataille.DEFANCE + " Points de vie)");
    }

    private void augmenterProchineHeroAtk() {
        int s = (int) (Math.random() * (50)) + 25;
        System.out.print("Prochine attaque augmenter " + s + "%");
        HeroBataille.ATK = (int) (heroBataille.getATK() * s / 100);
        System.out.println(" (+" + HeroBataille.ATK + " Critical)");
        System.out.println();
    }
}

