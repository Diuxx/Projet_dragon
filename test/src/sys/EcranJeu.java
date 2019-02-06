package sys;

import hud.Hud;
import jeu.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EcranJeu extends BasicGameState {

    /**
     * Variable de debug
     * Modifi certain élement d'affichage dans l'environnement de jeu.
     */
    public static final boolean DEBUG = true;
    public static final int ID = 2;

    /**
     * Gestion de le fenetre et de l'affichage
     */
    private GameContainer container;
    private StateBasedGame gameState;

    /**
     * Variable de jeu et d'affichage
     */
    private Message lesMessages; /* test */
    private Hero hero;
    private Carte carte;
    private Camera camera;

    /**
     * Version 0 de la class scenario / histoire du jeu.
     */
    private Scenario scenario;

    /**
     * Feuille de style contenant les images de notre hero
     */
    public static SpriteSheet spriteSheet;
    public static SpriteSheet spriteSheet_goblin;

    private Hud hud = new Hud();

    @Override
    public int getID() {
        return EcranJeu.ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        /**
         *
         */
        camera = new Camera();
        this.container = gameContainer;
        this.gameState = stateBasedGame;

        /**
         *
         */
        spriteSheet = new SpriteSheet("data/Tiny32.png", 32, 32);
        spriteSheet_goblin = new SpriteSheet("data/goblin.png", 64, 64);

        /**
         *
         */
        lesMessages = new Message();

        /**
         * chargement de la carte de jeu
         */
        carte = new Carte("data/dragon.tmx");

        /**
         * Chargement du hero de l'histoire
         */
        Point positionPersonnage = carte.getPositionPersonnage();
        hero = new Hero("LPDQL", positionPersonnage, new Taille(32, 32), 1200);

        // lesMessages = new jeu.Message();
        // lesMessages.add("c'est un test#un autre test#et un autre autre test #ça marche vraimeent bien #Salut salut");
        // [ .. next .. ]
        // si un élement du scenario n'est pas fini alors on charge le scénario... messages de joueur pnj etc..
        // ex :
        if(hero.artDeEpe == false) {
            /**
             * On charge le scenario de l'épe par exemple
             */
            scenario = new Scenario(Scenario.Art.EPE, carte, hero);
        } /* ..etc.. */
        hero.addPnj(scenario.getLesPnj());
        hero.addEnnemis(scenario.getLesEnnemis());
        hud.init(); // --
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        camera.translate(graphics, gameContainer);
        //graphics.translate(gameContainer.getWidth() / 2 - (int)this.xCamera, gameContainer.getHeight() / 2 - (int)this.yCamera);

        // affichage de la carte
        carte.afficher(carte.getMap().getLayerIndex("background1"));
        carte.afficher(carte.getMap().getLayerIndex("background2"));

        // affichage des personnages non joueurs et des ennemis
        if(scenario != null) {
            for(PersonnageNonJoueur pnj : scenario.getLesPnj()) {
                pnj.afficher(graphics);
                if(pnj.isParle()) {
                    lesMessages.add(pnj.getDialogue());
                    pnj.arreteDeParler();
                }
            }
            for(Ennemi unEnnemi : scenario.getLesEnnemis()) {
                if(!unEnnemi.isMort()) // si l'ennemi est vivant ?
                    unEnnemi.afficher(graphics);
            }
        }

        /**
         * Affichage du personnage contrôlé.
         */
        hero.afficher(graphics);

        /**
         * Buffer de messages.
         */
        if(lesMessages != null)
        {
            lesMessages.afficher(graphics, gameContainer, camera.getX(), camera.getY(), 5);
        }


        /**
         * image de positionné de tel façon que le personnage/ennemi/pnj puissent passer derrière.
         */
        carte.afficher(carte.getMap().getLayerIndex("overground1"));
        if(DEBUG) {
            carte.afficher(carte.getMap().getLayerIndex("porte"));
        }

        hud.render(graphics, this.hero);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        /**
         * Gestion des mouvement et des collision du hero
         * Les collisions sont géré automatiquement dans la class du hero
         */
        hero.controle(gameContainer);
        hero.mouvement(delta, carte.getMap());

        // affichage des mouvement des ennemi
        for(Ennemi unEnnemi : scenario.getLesEnnemis()) {
            unEnnemi.mouvement(delta, carte.getMap());
            if(unEnnemi.getBoundingBox().intersects(hero.getBoundingBox()))
            {
                // début d'un combat
                //System.out.println("Fight ! ");
            }
        }

        /**
         * mouvement de la camera
         */
        this.camera.update(gameContainer, this.carte, this.hero);
    }

    @Override
    public void keyReleased(int key, char c) {
        if(Input.KEY_W == key) {
            if(lesMessages != null) lesMessages.next();
        }
        // Affichage du menu du jeu.
        if(Input.KEY_ENTER == key)
        {
            gameState.enterState(EcranMenu.ID);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        // --
    }
}