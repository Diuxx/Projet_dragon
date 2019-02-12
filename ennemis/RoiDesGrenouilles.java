package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class RoiDesGrenouilles
 *
 * @author: Ez
 */
public class RoiDesGrenouilles extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    public RoiDesGrenouilles(Point p, Direction direction) {
        this(p.getX(), p.getY(), RoiDesGrenouilles.SIZE, direction, RoiDesGrenouilles.TIMER, RoiDesGrenouilles.VITESSE);
    }

    /**
     * Class constructor
     */
    public RoiDesGrenouilles(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Roi Des Grenouilles", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public RoiDesGrenouilles(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }
    

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  11);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  9);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  8);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  10);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  11);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  9);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  8);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  10);
    }
}