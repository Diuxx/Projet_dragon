package ennemis;


import jeu.Ennemi;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;

import java.awt.*;

/**
 * class Goblin
 *
 * @author: Diuxx
 */
public class Slim extends Ennemi {

    private float vitesse;

    /**
     * Class constructor
     */
    public Slim(Point positon, Taille taille, char direction, int timer) {
        super("Slim", positon.getX(), positon.getY(), taille.getLargeur(), taille.getLongeur(),
                50, direction, timer);
        this.chargerImage();
    }

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet, 0, 1,  15);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 1,  13);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 1,  12);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 1,  14);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 3,  15);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 3,  13);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 3,  12);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 3,  14);
    }
}
/*
        this.loadAnimation(EcranJeu.spriteSheet, 0, 1,  11);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 1,  9);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 1,  8);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 1,  10);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 3,  11);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 3,  9);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 3,  8);
        this.loadAnimation(EcranJeu.spriteSheet, 0, 3,  10);
 */