package sys;


/**
 * class Taille
 *
 * @author: Diuxx
 */
public class Taille {

    private int largeur;
    private int longeur;

    /**
     * Class constructor
     */
    public Taille(int largeur, int longeur) {
        this.largeur = largeur;
        this.longeur = longeur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        largeur = largeur;
    }

    public int getLongeur() {
        return longeur;
    }

    public void setLongeur(int longeur) {
        longeur = longeur;
    }
}
