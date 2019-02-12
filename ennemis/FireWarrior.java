package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class FireWarrior
 *
 * @author: Ez
 */
public class FireWarrior extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    public FireWarrior(Point p, Direction direction) {
        this(p.getX(), p.getY(), FireWarrior.SIZE, direction, FireWarrior.TIMER, FireWarrior.VITESSE);
    }

    /**
     * Class constructor
     */
    public FireWarrior(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Fire Warrior", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public FireWarrior(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }
    

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  11);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  9);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  8);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  10);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  11);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  9);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  8);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  10);
    }
}