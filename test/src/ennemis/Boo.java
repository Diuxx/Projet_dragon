package ennemis;


import jeu.Ennemi;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;
/**
 * class Boo
 *
 * @author: Diuxx
 */
public class Boo extends Ennemi {
    private float vitesse;

    /**
     * Class constructor
     */
    public Boo(Point positon, Taille taille, char direction, int timer) {
        super("Boo", positon.getX(), positon.getY(), taille.getLargeur(), taille.getLongeur(),
                300, direction, timer);
        this.chargerImage();
    }


    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet, 6, 7,  15);
        this.loadAnimation(EcranJeu.spriteSheet, 6, 7,  13);
        this.loadAnimation(EcranJeu.spriteSheet, 6, 7,  12);
        this.loadAnimation(EcranJeu.spriteSheet, 6, 7,  14);
        this.loadAnimation(EcranJeu.spriteSheet, 6, 9,  15);
        this.loadAnimation(EcranJeu.spriteSheet, 6, 9,  13);
        this.loadAnimation(EcranJeu.spriteSheet, 6, 9,  12);
        this.loadAnimation(EcranJeu.spriteSheet, 6, 9,  14);
    }
}
