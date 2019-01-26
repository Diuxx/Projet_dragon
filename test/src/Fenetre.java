import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class Fenetre extends BasicGame {

    private GameContainer container;
    private TiledMap grassMap;

    private float x = 200, y = 300;
    private int direction = 0;
    private boolean moving = false;
    private Animation[] animations = new Animation[8];

    private float xCamera = x, yCamera = y;

    // personnage contôlé par l'utilisateur.
    private Message lesMessages;
    private Hero hero;
    private Carte carte;

    // --
    public Fenetre() {
        super("Projet Dragon");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        SpriteSheet spriteSheet = new SpriteSheet("data/Tiny32.png", 32, 32);
        // chargement de la carte de jeu
        carte = new Carte("data/dragon.tmx");
        Point positionPersonnage = carte.getPositionPersonnage();
        System.out.println(positionPersonnage.getX() + " y : " + positionPersonnage.getY());


        // chargement du hero
        hero = new Hero("test", positionPersonnage.getX() * 32, positionPersonnage.getY() * 32, 100);
        hero.loadAnimation(spriteSheet, 6, 7,  11);
        hero.loadAnimation(spriteSheet, 6, 7,  9);
        hero.loadAnimation(spriteSheet, 6, 7,  8);
        hero.loadAnimation(spriteSheet, 6, 7,  10);
        hero.loadAnimation(spriteSheet, 6, 9,  11);
        hero.loadAnimation(spriteSheet, 6, 9,  9);
        hero.loadAnimation(spriteSheet, 6, 9,  8);
        hero.loadAnimation(spriteSheet, 6, 9,  10);

        lesMessages = new Message();
        lesMessages.add("c'est un test#un autre test#et un autre autre test #ça marche vraimeent bien #Salut salut");
        // [ .. next .. ]
    }

    @Override
    public void keyReleased(int key, char c) {
        hero.stop(); // arrêt du hero
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
        if(Input.KEY_N == key) {
            lesMessages.next();
        }
    }


    @Override
    public void keyPressed(int key, char c) {
        /*
         * Gestion des Mouvements du hero par l'utilisteur
         */
        hero.controle(key);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.translate(container.getWidth() / 2 - (int)this.xCamera, container.getHeight() / 2 - (int)this.yCamera);
        // affichage de la carte
        carte.afficher(0);
        carte.afficher(1);

        // affichage du hero
        hero.afficher(g);

        lesMessages.afficher(g, container, this.xCamera, this.yCamera, 5);
        /*g.setColor(Color.white);
        Rectangle rect = new Rectangle((int) this.xCamera - (container.getWidth() / 2) + 5,
                                       (int) this.yCamera + (container.getHeight() / 2) - 75,
                                    container.getWidth() - 10, 70);
        g.fill(rect);
        g.setColor(Color.black);
        g.drawString("test affichage d'un text random", rect.getX() + 5, rect.getY() + 5);*/

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
        hero.mouvement(delta, carte.getMap());
        this.xCamera = hero.getX();
        this.yCamera = hero.getY();
    }

    // --
    private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 150);
        }
        return animation;
    }
}