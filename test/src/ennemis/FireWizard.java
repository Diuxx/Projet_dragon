package ennemis;


import jeu.Ennemi;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class FireWizard
 *
 * @author: Ez
 */
public class FireWizard extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 2000;
    private static final float VITESSE = 0.05f;

    public FireWizard(Point p, Direction direction) {
        this(p.getX(), p.getY(), FireWizard.SIZE, direction, FireWizard.TIMER, FireWizard.VITESSE);
    }

    /**
     * Class constructor
     */
    public FireWizard(int x, int y, Taille taille, Direction direction, int timer, float vitesse) {
        super("The Fire Wizard", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse);
        this.chargerImage();
    }
    
    public FireWizard(Point positon, Taille taille, Direction direction, int timer, float vitesse) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse);
    }
    

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  7);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  5);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  4);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 1,  6);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  7);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  5);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  4);
        this.loadAnimation(EcranJeu.spriteSheet_Ennemis, 0, 3,  6);
    }
}