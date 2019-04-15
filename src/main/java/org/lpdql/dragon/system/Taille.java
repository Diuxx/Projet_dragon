package org.lpdql.dragon.system;

/**
 * class Taille
 *
 * @author: Diuxx
 */
public class Taille {

    public static final Taille BASIC_SIZE = new Taille(16, 16);
    public static final Taille LARGE_SIZE = new Taille(32, 32);
    public static final Taille BIG_SIZE = new Taille(64, 64);

    private int largeur;
    private int hauteur;

    /**
     * Class constructor
     */
    public Taille(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        largeur = largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setLongeur(int longeur) {
        longeur = longeur;
    }
}
