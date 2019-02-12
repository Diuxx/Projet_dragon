package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class MonstreMysterieux
 *
 * @author: Ez
 */
public class MonstreMysterieux extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    public MonstreMysterieux(Point p, Direction direction) {
        this(p.getX(), p.getY(), MonstreMysterieux.SIZE, direction, MonstreMysterieux.TIMER, MonstreMysterieux.VITESSE);
    }

    /**
     * Class constructor
     */
    public MonstreMysterieux(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("Monstre Mysterieux", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public MonstreMysterieux(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }
    

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  7);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  5);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  4);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 7,  6);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  7);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  5);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  4);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 6, 8,  6);
    }
}