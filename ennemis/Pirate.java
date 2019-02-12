package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class Pirate
 *
 * @author: Ez
 */
public class Pirate extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    public Pirate(Point p, Direction direction) {
        this(p.getX(), p.getY(), Pirate.SIZE, direction, Pirate.TIMER, Pirate.VITESSE);
    }

    /**
     * Class constructor
     */
    public Pirate(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Pirate", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public Pirate(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }
    

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  15);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  13);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  12);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  14);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  15);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  13);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  12);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  14);
    }
}