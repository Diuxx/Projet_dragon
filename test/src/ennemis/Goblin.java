package ennemis;
import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;


/**
 * class Goblin
 *
 * @author: Diuxx
 */
public class Goblin extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    public Goblin(Point p, Direction direction) {
        this(p.getX(), p.getY(), Goblin.SIZE, direction, Goblin.TIMER, Goblin.VITESSE);
    }

    /**
     * Class constructor
     */
    public Goblin(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Lutin", x, y, taille.getLargeur(), taille.getLongeur(),
                300, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public Goblin(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 0, 1, 2);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 0, 1, 3);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 0, 1, 0);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 0, 1, 1);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 1, 7, 2);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 1, 7, 3);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 1, 7, 0);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 1, 7, 1);
    }
}
