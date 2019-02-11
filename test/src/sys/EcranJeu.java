package sys;

import Bataille.Bataille;
import carte.Carte;
import hud.Hud;
import hud.Hud_menu;
import jeu.*;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import retest.Dragon;

import java.io.IOException;

public class EcranJeu extends BasicGameState {

    private boolean updatePaused = false;

    /**
     * Variable de debug
     * Modifie certains élements d'affichage dans l'environnement */
    public static final boolean DEBUG = true;
    public static final int ID = 2;

    /**
     * Gestion de la fenetre et de l'affichage */
    private GameContainer container;
    public static StateBasedGame gameState;

    /**
     * Variable de jeu et d'affichage */
    private Message lesMessages; /* test */
    private Carte carte;
    private Camera camera;

    /**
     * Version 0 de la class scenario / histoire du jeu. */
    private Scenario scenario;

    /**
     * Feuille de style contenant les images des personnage du jeu */
    public static SpriteSheet spriteSheet;
    public static SpriteSheet spriteSheet_goblin;
    public static SpriteSheet spriteSheet_Ennemis;
    public static SpriteSheet spriteSheet_Dragon;

    private Hud hud = new Hud();
    private Hud_menu menu = new Hud_menu();

    @Override
    public int getID() {
        return EcranJeu.ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        // initalisation de la fenètre
        this.container = gameContainer;
        this.gameState = stateBasedGame;

        // chargement des textures
        spriteSheet = new SpriteSheet("data/Tiny32.png", 32, 32);
        spriteSheet_goblin = new SpriteSheet("data/goblin.png", 64, 64);
        spriteSheet_Ennemis = new SpriteSheet("data/Ennemis.png", 32, 32);
        spriteSheet_Dragon = new SpriteSheet("data/Dragon.png", 96, 96);

        lesMessages = new Message();

        // chargement de la carte de jeu
        carte = new Carte("data/dragon.tmx");

        // Chargement du hero de l'histoire
        Point p = carte.getPositionPersonnage();
        Dragon.setLeHero(new Hero("LPDQL", p));

        // initialisation de la camera
        camera = new Camera(Dragon.getLeHero().getX(), Dragon.getLeHero().getY());

        // chargement de l'histoire
        if(Dragon.getLeHero().artDeEpe == false) {
            // scenario de l'épé
            scenario = new Scenario(Scenario.Art.EPE, carte, Dragon.getLeHero());
        } /* ..etc.. */
        Dragon.getLeHero().addPnj(scenario.getLesPnj());
        Dragon.getLeHero().addEnnemis(scenario.getLesEnnemis());
        hud.init(); // --
        menu.init(gameContainer);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        camera.translate(graphics, gameContainer);

        // affichage de la carte
        carte.afficher(carte.getMap().getLayerIndex("background1"));
        carte.afficher(carte.getMap().getLayerIndex("background2"));

        // affichage des personnages non joueurs et des ennemis
        if(scenario != null) {
            scenario.afficherPnj(graphics, this.lesMessages);;
            scenario.afficherEnnemis(graphics);
        }

        // Affichage du hero
        Dragon.getLeHero().afficher(graphics);

        // affichage des messages
        if(lesMessages.afficher(graphics, gameContainer, this.camera) || menu.isShowing())
        {
            this.setUpdatePaused(true);
        } else {
            if(this.isUpdatePaused())
                this.setUpdatePaused(false);
        }

        // images positionné de telle façon
        // que les personnages/ennemis/pnjs puissent passer derrière.
        carte.afficher(carte.getMap().getLayerIndex("overground1"));
        if(DEBUG) {
            carte.afficher(carte.getMap().getLayerIndex("porte"));
        }
        hud.render(graphics, Dragon.getLeHero());

        if(menu.isShowing()) {
            switch(menu.render(gameContainer, graphics)) {
                case EXITGAME:
                    container.exit();
                    break;
                case BACK:
                    menu.setShowing(false);
                    this.setUpdatePaused(false);
                    break;
                case NONE:
                    /*if(gameContainer.getInput().isKeyDown(Input.KEY_ENTER)) {
                        menu.setShowing(true);
                        this.setUpdatePaused(true);

                        System.out.println("is paused : " + this.isUpdatePaused());
                    }*/
                    break;
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if(this.isUpdatePaused())
            return;

         // Gestion des mouvements et des collisions du hero
         // Les collisions sont géré automatiquement dans la class du herp
        Dragon.getLeHero().controle(gameContainer);
        Dragon.getLeHero().mouvement(delta, carte.getMap());

        // affichage des mouvement des ennemi
        this.scenario.mouvement(this.carte, delta);

        // mouvement de la camera
        this.camera.update(gameContainer, this.carte, Dragon.getLeHero());
    }

    @Override
    public void keyReleased(int key, char c) {
        if(Input.KEY_W == key) {
            if(lesMessages != null) lesMessages.next();
        }
        // Affichage du menu du jeu.
        if(Input.KEY_ENTER == key && !menu.isShowing()) {
            menu.setShowing(true);
            this.setUpdatePaused(true);

            System.out.println("is paused : " + this.isUpdatePaused());
        }
    }

    @Override
    public void keyPressed(int key, char c) {


    }

    public boolean isUpdatePaused() {
        return updatePaused;
    }

    public void setUpdatePaused(boolean updatePaused) {
        this.updatePaused = updatePaused;
    }
}