package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class Dragon
 *
 * @author: Ez
 */
public class Dragon extends Ennemi {


    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    /**
     * Class constructor
     */
    public Dragon(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Dragon", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public Dragon(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }

    public Dragon(Point p, Direction direction) {
        this(p.getX(), p.getY(), Dragon.SIZE, direction, Dragon.TIMER, Dragon.VITESSE);
    }

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Dragon, 0, 1,  3);
        this.loadAnimation(EcranJeu.spriteSheet_Dragon, 0, 1,  1);
        this.loadAnimation(EcranJeu.spriteSheet_Dragon, 0, 1,  0);
        this.loadAnimation(EcranJeu.spriteSheet_Dragon, 0, 1,  2);
        this.loadAnimation(EcranJeu.spriteSheet_Dragon, 0, 3,  3);
        this.loadAnimation(EcranJeu.spriteSheet_Dragon, 0, 3,  1);
        this.loadAnimation(EcranJeu.spriteSheet_Dragon, 0, 3,  0);
        this.loadAnimation(EcranJeu.spriteSheet_Dragon, 0, 3,  2);
    }
}