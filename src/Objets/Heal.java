package Objets;


import Mondes.Ressources;
import personnages.Hero;
import org.newdawn.slick.Graphics;
import sys.Point;
import sys.Taille;

/**
 * class Heal
 *
 * @author: Diuxx
 */
public class Heal extends Objet {

    private long tempsDerniereAction;
    private long tempsAvantReApparition = 5000;
    private boolean disponible;

    /**
     * Class constructor
     */
    public Heal(Point position, int p) {
        super("Heal", position, Taille.BASIC_SIZE, p);
        this.disponible = true;
        tempsDerniereAction = 0l;

        this.loadAnimation(Ressources.healSheet, 0, 2, 0);
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     *
     * @param h
     */
    @Override
    public void interaction(Hero h) {
        if(!isDisponible())
            return;

        h.setPointDeVieActuel(h.getPointDeVie());

        setDisponible(false);
        tempsDerniereAction = System.currentTimeMillis();
        System.err.println("Heal ramassé ! hid = " + this.getPositionSurMap());
    }

    @Override
    public void afficher(Graphics g) {
        if(!isDisponible()) {
            if(System.currentTimeMillis() - this.tempsDerniereAction >= tempsAvantReApparition) {
                setDisponible(true);
            }
        } else {
            super.afficher(g);
        }
    }
}
