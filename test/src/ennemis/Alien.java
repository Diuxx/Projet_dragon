package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class Alien
 *
 * @author: Ez
 */
public class Alien extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    /**
     * Class constructor
     */
    public Alien(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Alien", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public Alien(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }

    public Alien(Point p, Direction direction) {
        this(p.getX(), p.getY(), Alien.SIZE, direction, Alien.TIMER, Alien.VITESSE);
    }
    

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 9, 10,  11);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 9, 10,  9);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 9, 10,  8);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 9, 10,  10);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 9, 11,  11);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 9, 11,  9);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 9, 11,  8);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 9, 11,  10);
    }
}