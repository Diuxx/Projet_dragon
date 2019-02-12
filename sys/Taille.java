package sys;


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
