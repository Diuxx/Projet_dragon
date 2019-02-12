package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;

/**
 * class Lutin
 *
 * @author: Ez
 */
public class Lutin extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    public Lutin(Point p, Direction direction) {
        this(p.getX(), p.getY(), Lutin.SIZE, direction, Lutin.TIMER, Lutin.VITESSE);
    }

    /**
     * Class constructor
     */
    public Lutin(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Lutin", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public Lutin(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }

	private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  3);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  1);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  0);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  2);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  3);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  1);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  0);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  2);
    }
}