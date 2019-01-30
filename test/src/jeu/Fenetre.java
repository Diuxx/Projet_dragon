package jeu;

import org.newdawn.slick.*;

public class Fenetre extends BasicGame {

    public static boolean DEBUG = true;

    // fenetre
    private GameContainer container;

    private float xCamera = 0, yCamera = 0;

    // personnage contôlé par l'utilisateur.
    private Message lesMessages; /* test */
    private Hero hero;
    private Carte carte;

    // test du scénario
    private Scenario scenario;

    // test sprite
    public static SpriteSheet spriteSheet;

    // --
    public Fenetre() {
        super("Projet Dragon");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        spriteSheet = new SpriteSheet("data/Tiny32.png", 32, 32);

        lesMessages = new Message();
        // chargement de la carte de jeu
        carte = new Carte("data/dragon.tmx");
        Point positionPersonnage = carte.getPositionPersonnage();

        // chargement du hero
        hero = new Hero("test", positionPersonnage.getX(), positionPersonnage.getY(), 32, 32,100);
        hero.loadAnimation(spriteSheet, 6, 7,  11);
        hero.loadAnimation(spriteSheet, 6, 7,  9);
        hero.loadAnimation(spriteSheet, 6, 7,  8);
        hero.loadAnimation(spriteSheet, 6, 7,  10);
        hero.loadAnimation(spriteSheet, 6, 9,  11);
        hero.loadAnimation(spriteSheet, 6, 9,  9);
        hero.loadAnimation(spriteSheet, 6, 9,  8);
        hero.loadAnimation(spriteSheet, 6, 9,  10);

        // lesMessages = new jeu.Message();
        // lesMessages.add("c'est un test#un autre test#et un autre autre test #ça marche vraimeent bien #Salut salut");
        // [ .. next .. ]
        // si un élement du scenario n'est pas fini alors on charge le scénario... messages de joueur pnj etc..
        // ex :
        if(hero.artDeEpe == false) {
            /**
             * On charge le scenario de l'épe par exemple
             */
            scenario = new Scenario(Scenario.Art.EPE, carte);
        } /* ..etc.. */
        hero.addPnj(scenario.getLesPnj());
    }

    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
        if(Input.KEY_W == key) {
            if(lesMessages != null) lesMessages.next();
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        // --
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.translate(container.getWidth() / 2 - (int)this.xCamera, container.getHeight() / 2 - (int)this.yCamera);
        // affichage de la carte
        carte.afficher(0);
        carte.afficher(1);

        if(scenario != null) {
            for(PersonnageNonJoueur pnj : scenario.getLesPnj()) {
                pnj.afficher(g);
                if(pnj.isParle()) {
                    lesMessages.add(pnj.getDialogue());
                    pnj.arreteDeParler();
                }
            }
            for(Ennemi unEnnemi : scenario.getLesEnnemis()) {
                unEnnemi.afficher(g);
            }
        }

        // affichage du hero
        hero.afficher(g);

        if(lesMessages != null)
            lesMessages.afficher(g, container, this.xCamera, this.yCamera, 5);

        // background background
        carte.afficher(2);
        carte.afficher(3);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        /*
         * Gestion des mouvement et des collision du hero
         * Les collisions sont géré automatiquement
         */
        hero.controle(container);
        hero.mouvement(delta, carte.getMap());

        for(Ennemi unEnnemi : scenario.getLesEnnemis()) {
            unEnnemi.mouvement(delta, carte.getMap());
        }

        this.xCamera = hero.getX();
        this.yCamera = hero.getY();
    }
}