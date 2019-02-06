package ennemis;


import jeu.Ennemi;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;

/**
 * class Chauve
 *
 * @author: Diuxx
 */
public class Chauve extends Ennemi {

    private float vitesse;

    /**
     * Class constructor
     */
    public Chauve(Point positon, Taille taille, char direction, int timer) {
        super("Chauve Souris", positon.getX(), positon.getY(), taille.getLargeur(), taille.getLongeur(),
                150, direction, timer);
        this.chargerImage();
    }

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet, 3, 4,  15);
        this.loadAnimation(EcranJeu.spriteSheet, 3, 4,  13);
        this.loadAnimation(EcranJeu.spriteSheet, 3, 4,  12);
        this.loadAnimation(EcranJeu.spriteSheet, 3, 4,  14);
        this.loadAnimation(EcranJeu.spriteSheet, 3, 6,  15);
        this.loadAnimation(EcranJeu.spriteSheet, 3, 6,  13);
        this.loadAnimation(EcranJeu.spriteSheet, 3, 6,  12);
        this.loadAnimation(EcranJeu.spriteSheet, 3, 6,  14);
    }
}