package jeu;

/**
 * class jeu.Ennemi
 *
 * @author: Diuxx
 */
public class Ennemi extends Personnage {

    // --
    private float timeSinceTrigger = 0;


    /**
     * Class constructor
     */
    public Ennemi(String nom, float x, float y, int w, int h,  int pointDeVie) {
        super(nom, x, y, w, h, pointDeVie);
    }


    /**
     *
     * @param nom
     * @param pos
     */
    public Ennemi(String nom, Point pos, int w, int h, int pointDeVie) {
        super(nom, pos, w, h, pointDeVie);
    }


    public void move(int delta) {

    }

    /**
     *
     * @return
     */
    public float getTimeSinceTrigger() {
        return timeSinceTrigger;
    }

    /**
     *
     * @param timeSinceTrigger
     */
    public void setTimeSinceTrigger(float timeSinceTrigger) {
        this.timeSinceTrigger = timeSinceTrigger;
    }
}
