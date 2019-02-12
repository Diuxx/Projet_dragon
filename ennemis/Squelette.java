package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;

/**
 * class Squelette
 *
 * @author: Ez
 */
public class Squelette extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1500;
    private static final float VITESSE = 0.04f;

    public Squelette(Point p, Direction direction) {
        this(p.getX(), p.getY(), Squelette.SIZE, direction, Squelette.TIMER, Squelette.VITESSE);
    }

    /**
     * Class constructor
     */
    public Squelette(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Squelette", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public Squelette(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }

	private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 4,  3);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 4,  1);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 4,  0);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 4,  2);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 5,  3);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 5,  1);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 5,  0);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 3, 5,  2);
    }
}