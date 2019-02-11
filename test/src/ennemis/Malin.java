package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class Malin
 *
 * @author: Ez
 */
public class Malin extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    public Malin(Point p, Direction direction) {
        this(p.getX(), p.getY(), Malin.SIZE, direction, Malin.TIMER, Malin.VITESSE);
    }

    /**
     * Class constructor
     */
    public Malin(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Malin", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public Malin(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }
    

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 4,  11);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 4,  9);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 4,  8);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 4,  10);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 5,  11);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 5,  9);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 5,  8);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 5,  10);
    }
}